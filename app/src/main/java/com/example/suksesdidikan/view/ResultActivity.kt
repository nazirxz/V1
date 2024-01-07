package com.example.suksesdidikan.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.R
import com.example.suksesdidikan.databinding.ActivityResultBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var databaseReference: DatabaseReference
    private var sessionStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionStartTime = SystemClock.uptimeMillis()
        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")
        val kelas = intent.getStringExtra("USER_KELAS")
        val usia = intent.getStringExtra("USER_USIA")
        // Inisialisasi Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("performasiswa")
        binding.bottomNavigation.selectedItemId = R.id.bottom_result
        binding.bottomNavigation.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@ResultActivity, MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)
                    intent.putExtra("USER_KELAS",kelas)
                    intent.putExtra("USER_USIA",usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> {
                    val intent = Intent(this@ResultActivity, DaftarMateriActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)
                    intent.putExtra("USER_KELAS",kelas)
                    intent.putExtra("USER_USIA",usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_result -> {
                    true
                }
                // Tambahkan case untuk item lain jika diperlukan
                else -> false
            }
        }

        // Ambil data dari Firebase
        getDataFromFirebase()


        //inisialisasi menghitung durasi user
        updateUserLastActiveTimestamp(userId!!)
    }
    // Fungsi untuk mengambil data table performasiswa dari Firebase
    private fun getDataFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val rajinCount = snapshot.child("Rajin").value.toString().toFloat()
                    val biasaCount = snapshot.child("Biasa").value.toString().toFloat()
                    val malasCount = snapshot.child("Malas").value.toString().toFloat()

                    // Buat Pie Chart
                    val pieEntries = listOf(
                        PieEntry(rajinCount, "Rajin"),
                        PieEntry(biasaCount, "Biasa"),
                        PieEntry(malasCount, "Malas")
                    )
                    createPieChart(pieEntries)

                    // Buat Bar Chart
                    val barEntries = listOf(
                        BarEntry(0f, rajinCount),
                        BarEntry(1f, biasaCount),
                        BarEntry(2f, malasCount)
                    )
                    createBarChart(barEntries)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Error handling
            }
        })
    }

    //membuat pie chart
    private fun createPieChart(entries: List<PieEntry>) {
        val dataSet = PieDataSet(entries, "Peforma siswa")
        dataSet.colors = mutableListOf(Color.RED, Color.GREEN, Color.BLUE)
        dataSet.valueTextSize = 12f

        val pieData = PieData(dataSet)
        val pieChart = binding.pieChart
        pieChart.data = pieData
        pieChart.invalidate()
    }
    //membuat bar chart
    private fun createBarChart(entries: List<BarEntry>) {
        val dataSet = BarDataSet(entries, "Peforma siswa")
        dataSet.colors = mutableListOf(Color.RED, Color.GREEN, Color.BLUE)
        dataSet.valueTextSize = 12f

        val barData = BarData(dataSet)
        val barChart = binding.barChart
        barChart.data = barData
        barChart.setFitBars(true)
        barChart.invalidate()
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

    //jika user menekan tombol back
    override fun onBackPressed() {
        AlertDialog.Builder(this@ResultActivity, R.style.AlertDialogTheme)
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