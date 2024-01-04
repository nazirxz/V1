package com.example.suksesdidikan

import Buku
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.DummyData.dummyList
import com.example.suksesdidikan.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var sessionStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Inisialisasi sessionStartTime saat aplikasi dimulai
        sessionStartTime = SystemClock.uptimeMillis()

        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")
        val kelas = intent.getStringExtra("USER_KELAS")
        val usia = intent.getStringExtra("USER_USIA")

        val welcomeText = "Hello $userName"
        binding.tvWelcome.text = welcomeText

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@MainActivity, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }
        //filter data kelas
        binding.btn7.setOnClickListener {
            filterByKelas("VII")
        }

        binding.btn8.setOnClickListener {
            filterByKelas("VIII")
        }

        binding.btn9.setOnClickListener {
            filterByKelas("IX")
        }


        binding.bottomNavigation.selectedItemId = R.id.bottom_home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    true
                }
                R.id.bottom_materi -> {
                    val intent = Intent(this@MainActivity, DaftarMateriActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)
                    intent.putExtra("USER_KELAS",kelas)
                    intent.putExtra("USER_USIA",usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_result -> {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)
                    intent.putExtra("USER_KELAS",kelas)
                    intent.putExtra("USER_USIA",usia)
                    startActivity(intent)
                    finish()
                    true
                }
                // Tambahkan case untuk item lain jika diperlukan
                else -> false
            }
        }
        // Tambahkan logging userId di si
        // Handle lastActiveTimestamp update

        updateUserLastActiveTimestamp(userId!!)


        // Membuat adapter dan mengatur ke RecyclerView
        val adapter = BukuAdapter(dummyList) { selectedItem ->
            // Lakukan apa pun yang ingin Anda lakukan ketika item diklik di sini
            // Contohnya, menuju ke halaman lain dengan data yang dipilih
            val intent = Intent(this, KursusActivity::class.java)
            intent.putExtra("USER_NAME", userName)
            intent.putExtra("USER_ID",userId)
            intent.putExtra("USER_KELAS",kelas)
            intent.putExtra("USER_USIA",usia)
            intent.putRekomenExtra(selectedItem)
            startActivity(intent)
        }

        // Mengatur LayoutManager untuk RecyclerView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRekomen.layoutManager = layoutManager
        binding.rvRekomen.adapter = adapter

    }
    private fun filterByKelas(kelas: String) {
        val filteredList = dummyList.filter { it.kelas == kelas }
        (binding.rvRekomen.adapter as? BukuAdapter)?.updateList(filteredList)
    }
    fun Intent.putRekomenExtra(rekomen: Buku) {
        with(rekomen) {
            putExtra("AVATAR", gambar)
            putExtra("MAPELAJARAN", matapelajaran)
            putExtra("DESKRIPSI", deskripsi)
        }
    }
    override fun onResume() {
        super.onResume()
        val userId = intent.getStringExtra("USER_ID")
        updateUserLastActiveTimestamp(userId!!)

    }

    override fun onPause() {
        super.onPause()
        val userId = intent.getStringExtra("USER_ID")
        updateUserLastActiveTimestamp(userId!!)

    }

    private fun updateUserLastActiveTimestamp(userId: String) {
        val database = FirebaseDatabase.getInstance().reference
        val sessionEndTime = SystemClock.uptimeMillis()
        val sessionDuration = sessionEndTime - sessionStartTime // Menghitung durasi sesi
        val userName = intent.getStringExtra("USER_NAME")
        val kelas = intent.getStringExtra("USER_KELAS") ?: "" // Ubah sesuai tipe data yang diharapkan
        val usia = intent.getStringExtra("USER_USIA") ?: "" // Ubah sesuai tipe data yang diharapkan
        Log.d("Firebase", "Session Duration in Millis: $sessionDuration") // Tambahkan log untuk durasi sesi

        val aktivitasRef = database.child("aktivitas").child(userId)

        aktivitasRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Jika entri ada, perbarui data yang ada
                    val existingData = dataSnapshot.value as? Map<String, Any>

                    existingData?.let {
                        val existingDuration = it["lastActiveDuration"] as? Long ?: 0
                        val updatedDuration = existingDuration + sessionDuration

                        val updateMap = HashMap<String, Any>()
                        updateMap["lastActiveDuration"] = updatedDuration
                        Log.d("Data","Durasi $updatedDuration.toString()")
                        updateMap["userName"] = userName!!
                        Log.d("Data", "Username $userName")
                        updateMap["kelas"] = kelas!!// Tambahkan kelas
                        Log.d("Data", "Kelas $kelas")
                        updateMap["usia"] = usia!! // Tambahkan usia
                        Log.d("Data", "Usia $usia")
                        aktivitasRef.updateChildren(updateMap)
                            .addOnSuccessListener {
                                Log.d("Firebase", "Updated lastActiveDuration for user $userId")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firebase", "Failed to update lastActiveDuration for user $userId: ${e.message}")
                            }
                    }
                } else {
                    // Jika entri tidak ada, buat entri baru
                    val sessionData = HashMap<String, Any>()
                    sessionData["lastActiveDuration"] = sessionDuration // Menyimpan durasi sesi
                    Log.d("Data", "Durasi $sessionDuration")

                    sessionData["userName"] = userName!!
                    Log.d("Data", "Username $userName")

                    sessionData["kelas"] = kelas!! // Tambahkan kelas
                    Log.d("Data", "Kelas $kelas")

                    sessionData["usia"] = usia!! // Tambahkan usia
                    Log.d("Data", "Usia $usia")

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

}
