package com.example.suksesdidikan.view

import com.example.suksesdidikan.model.Buku
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.utils.DummyData.dummyList
import com.example.suksesdidikan.R
import com.example.suksesdidikan.adapter.DaftarMatpelAdapter
import com.example.suksesdidikan.databinding.ActivityDaftarmatapelajaranBinding
import com.example.suksesdidikan.model.BabDetail
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.Serializable

class DaftarMateriActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDaftarmatapelajaranBinding
    private lateinit var daftarMatpelAdapter: DaftarMatpelAdapter
    private var sessionStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarmatapelajaranBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionStartTime = SystemClock.uptimeMillis()

        // Mendapatkan data dari intent
        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")
        val kelas = intent.getStringExtra("USER_KELAS")
        val usia = intent.getStringExtra("USER_USIA")

        // Menangani bottom navigation
        binding.bottomNavigation.selectedItemId = R.id.bottom_materi
        binding.bottomNavigation.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    // Pindah ke MainActivity dengan membawa data pengguna yang diperlukan
                    val intent = Intent(this@DaftarMateriActivity, MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID", userId)
                    intent.putExtra("USER_KELAS", kelas)
                    intent.putExtra("USER_USIA", usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> {
                    true // Tidak ada tindakan karena sudah berada di halaman yang sama
                }
                R.id.bottom_result -> {
                    // Pindah ke ResultActivity dengan membawa data pengguna yang diperlukan
                    val intent = Intent(this@DaftarMateriActivity, ResultActivity::class.java)
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

        // Inisialisasi adapter dengan dummyList
        daftarMatpelAdapter = DaftarMatpelAdapter(dummyList)

        // Set adapter ke RecyclerView
        binding.rvMataPelajaran.adapter = daftarMatpelAdapter

        // Set layout manager ke RecyclerView (e.g., LinearLayoutManager)
        binding.rvMataPelajaran.layoutManager = LinearLayoutManager(this)

        // Menangani klik item pada adapter untuk navigasi ke DaftarBabActivity
        daftarMatpelAdapter.setOnItemClickListener(object : DaftarMatpelAdapter.OnItemClickListener {
            override fun onItemClick(buku: Buku) {
                val intent = Intent(this@DaftarMateriActivity, DaftarBabActivity::class.java)

                // Mengumpulkan semua detail bab ke dalam ArrayList
                val babDetails = ArrayList<BabDetail>()
                for (babInfo in buku.bab) {
                    babDetails.addAll(babInfo.isi)
                }

                // Mengirim ArrayList babDetails dan mata pelajaran ke activity berikutnya
                intent.putExtra("BAB_DETAILS", babDetails as Serializable)
                intent.putExtra("MAPELAJARAN", buku.matapelajaran)
                intent.putExtra("USER_NAME", userName)
                intent.putExtra("USER_ID", userId)
                intent.putExtra("USER_KELAS", kelas)
                intent.putExtra("USER_USIA", usia)
                startActivity(intent)
            }
        })

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

    // Override onBackPressed untuk menampilkan dialog konfirmasi keluar aplikasi
    override fun onBackPressed() {
        AlertDialog.Builder(this@DaftarMateriActivity, R.style.AlertDialogTheme)
            .setTitle("Keluar dari Aplikasi")
            .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            .setPositiveButton("Iya") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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
