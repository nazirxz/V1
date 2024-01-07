package com.example.suksesdidikan.view

import com.example.suksesdidikan.model.Buku
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.utils.DummyData.dummyList
import com.example.suksesdidikan.R
import com.example.suksesdidikan.adapter.BabAdapter
import com.example.suksesdidikan.databinding.ActivityKursusBinding
import com.example.suksesdidikan.model.BabInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class KursusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKursusBinding
    private var sessionStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKursusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionStartTime = SystemClock.uptimeMillis()

        // Ambil data dari Intent yang dikirim
        val avatar = intent.getIntExtra("AVATAR", 0)
        val matapelajaran = intent.getStringExtra("MAPELAJARAN")
        val deskripsi = intent.getStringExtra("DESKRIPSI")
        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")
        val kelas = intent.getStringExtra("USER_KELAS")
        val usia = intent.getStringExtra("USER_USIA")



        // Tampilkan data dalam layout activity_kursus
        binding.tvMatapelajaran.text = matapelajaran
        binding.tvDescription.text = deskripsi
        binding.avatar.setImageResource(avatar)
        binding.tvKursus.text = matapelajaran

        // Handle bottom navigation
        binding.bottomNavigation.selectedItemId = R.id.bottom_home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    // Intent ke MainActivity
                    val intent = Intent(this@KursusActivity, MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID", userId)
                    intent.putExtra("USER_KELAS", kelas)
                    intent.putExtra("USER_USIA", usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> {
                    // Intent ke DaftarMateriActivity
                    val intent = Intent(this@KursusActivity, DaftarMateriActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID", userId)
                    intent.putExtra("USER_KELAS", kelas)
                    intent.putExtra("USER_USIA", usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_result -> {
                    // Intent ke ResultActivity
                    val intent = Intent(this@KursusActivity, ResultActivity::class.java)
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

        // Handle back button click
        binding.back.setOnClickListener {
            finish()
        }

        // Ambil daftar bab yang terpilih
        val selectedBabList = getSelectedBabList(dummyList, matapelajaran)
        val adapter = BabAdapter(selectedBabList)

        // Atur RecyclerView
        binding.rvBab.layoutManager = LinearLayoutManager(this)
        binding.rvBab.adapter = adapter

        // Handle item click pada adapter
        adapter.setOnItemClickListener { babInfo ->
            // Intent ke DaftarBabActivity dengan data yang diperlukan
            val intent = Intent(this@KursusActivity, DaftarBabActivity::class.java)
            intent.putExtra("USER_NAME", userName)
            intent.putExtra("MAPELAJARAN", matapelajaran)
            intent.putExtra("USER_ID", userId)
            intent.putExtra("USER_KELAS", kelas)
            intent.putExtra("USER_USIA", usia)
            // Tambahkan data lain jika diperlukan
            startActivity(intent)
        }
        // Handle lastActiveTimestamp update
        updateUserLastActiveTimestamp(userId!!)
    }
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

    // Fungsi untuk mendapatkan daftar bab yang terpilih berdasarkan mata pelajaran
    fun getSelectedBabList(data: List<Buku>, mataPelajaran: String?): List<BabInfo> {
        val selectedBabList = mutableListOf<BabInfo>()
        mataPelajaran?.let { selectedMapel ->
            for (buku in data) {
                if (buku.matapelajaran.equals(selectedMapel, ignoreCase = true)) {
                    selectedBabList.addAll(buku.bab)
                }
            }
        }
        return selectedBabList
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
