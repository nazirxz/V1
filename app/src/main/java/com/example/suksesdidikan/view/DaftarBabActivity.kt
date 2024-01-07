package com.example.suksesdidikan.view

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.utils.DummyData
import com.example.suksesdidikan.utils.DummyData.dummyList
import com.example.suksesdidikan.R
import com.example.suksesdidikan.adapter.DaftarBabAdapter
import com.example.suksesdidikan.databinding.ActivityDaftarbabBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DaftarBabActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarbabBinding
    private lateinit var daftarBabAdapter: DaftarBabAdapter
    private var sessionStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarbabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionStartTime = SystemClock.uptimeMillis()

        // Mendapatkan data dari intent
        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")
        val matapelajaran = intent.getStringExtra("MAPELAJARAN") ?: ""
        val kelas = intent.getStringExtra("USER_KELAS")
        val usia = intent.getStringExtra("USER_USIA")

        // Mendapatkan daftar BabInfo yang difilter berdasarkan mata pelajaran yang dipilih
        val filteredBabInfoList = DummyData.getAllBabInfoFromBooks(matapelajaran)

        // Menyiapkan adapter dengan daftar BabInfo yang difilter dan daftar lengkap Buku
        daftarBabAdapter = DaftarBabAdapter(filteredBabInfoList, dummyList)
        binding.rvBab.adapter = daftarBabAdapter
        binding.rvBab.layoutManager = LinearLayoutManager(this)

        // Menangani navigasi bottom navigation
        binding.bottomNavigation.selectedItemId = R.id.bottom_materi
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    // Pindah ke MainActivity dengan membawa data pengguna yang diperlukan
                    val intent = Intent(this@DaftarBabActivity, MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID", userId)
                    intent.putExtra("USER_KELAS", kelas)
                    intent.putExtra("USER_USIA", usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> {
                    // Tetap berada di DaftarMateriActivity dengan membawa data pengguna yang diperlukan
                    val intent = Intent(this@DaftarBabActivity, DaftarMateriActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID", userId)
                    intent.putExtra("USER_KELAS", kelas)
                    intent.putExtra("USER_USIA", usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_result -> {
                    // Pindah ke ResultActivity dengan membawa data pengguna yang diperlukan
                    val intent = Intent(this@DaftarBabActivity, ResultActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID", userId)
                    intent.putExtra("USER_KELAS", kelas)
                    intent.putExtra("USER_USIA", usia)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        // Menangani klik item pada adapter untuk navigasi ke BelajarMateriActivity
        daftarBabAdapter.setOnItemClickListener { babDetail, babInfo ->
            val intent = Intent(this, BelajarMateriActivity::class.java)
            intent.putExtra("ISI_BAB", babDetail.isi)
            intent.putExtra("NAMA_BAB", babDetail.judul)
            intent.putExtra("BAB", babInfo.bab)
            intent.putExtra("USER_NAME", userName)
            intent.putExtra("USER_ID", userId)
            intent.putExtra("USER_KELAS", kelas)
            intent.putExtra("USER_USIA", usia)
            startActivity(intent)
        }

        // Tombol kembali
        binding.btnBack.setOnClickListener {
            finish()
        }

        //inisialisasi menghitung durasi user
        updateUserLastActiveTimestamp(userId!!)
    }
    //fungsi untuk menyimpan durasi user membuka aplikasi
    private fun updateUserLastActiveTimestamp(userId: String) {
        val database = FirebaseDatabase.getInstance().reference
        val sessionEndTime = SystemClock.uptimeMillis()
        val sessionDuration = sessionEndTime - sessionStartTime
        val userName = intent.getStringExtra("USER_NAME")
        val kelas = intent.getStringExtra("USER_KELAS") ?: ""
        val usia = intent.getStringExtra("USER_USIA") ?: ""

        val aktivitasRef = database.child("aktivitas").child(userId)

        aktivitasRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val existingData = dataSnapshot.value as? Map<String, Any>

                    existingData?.let {
                        val existingDuration = it["lastActiveDuration"] as? Long ?: 0
                        val updatedDuration = existingDuration + sessionDuration

                        val updateMap = HashMap<String, Any>()
                        updateMap["lastActiveDuration"] = updatedDuration
                        updateMap["userName"] = userName!!
                        updateMap["kelas"] = kelas!!
                        updateMap["usia"] = usia!!
                        aktivitasRef.updateChildren(updateMap)
                            .addOnSuccessListener {
                                Log.d("Firebase", "Updated lastActiveDuration for user $userId")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firebase", "Failed to update lastActiveDuration for user $userId: ${e.message}")
                            }
                    }
                } else {
                    val sessionData = HashMap<String, Any>()
                    sessionData["lastActiveDuration"] = sessionDuration
                    sessionData["userName"] = userName!!
                    sessionData["kelas"] = kelas!!
                    sessionData["usia"] = usia!!

                    database.child("aktivitas").child(userId).setValue(sessionData)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Created new entry for lastActiveDuration for user $userId")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firebase", "Failed to create new entry for lastActiveDuration for user $userId: ${e.message}")
                        }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Error: ${databaseError.message}")
            }
        })
    }

    //Fungsi jika aplikasi dibuka kembali
    override fun onResume() {
        super.onResume()
        val userId = intent.getStringExtra("USER_ID")
        updateUserLastActiveTimestamp(userId!!)

    }

    //Fungsi jika aplikasi membuka app luar
    override fun onPause() {
        super.onPause()
        val userId = intent.getStringExtra("USER_ID")
        updateUserLastActiveTimestamp(userId!!)

    }
}