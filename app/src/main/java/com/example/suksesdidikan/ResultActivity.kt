package com.example.suksesdidikan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivityResultBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pieChartData()
        columnChartData()
        binding.bottomNavigation.selectedItemId = R.id.bottom_result
        binding.bottomNavigation.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@ResultActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> {
                    val intent = Intent(this@ResultActivity,DaftarMateriActivity::class.java)
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
    }

    private fun pieChartData() {
        val entries = listOf(
            PieEntry(98f, "Fatur"),  // Data Fatur dengan nilai 98
            PieEntry(100f, "Wahyu"), // Data Wahyu dengan nilai 100
            PieEntry(78f, "Ahmad")   // Data Ahmad dengan nilai 78
        )

        val dataSet = PieDataSet(entries, "Mahasiswa Berprestasi")
        dataSet.colors = mutableListOf(Color.RED, Color.GREEN, Color.BLUE) // Warna untuk setiap bagian
        dataSet.valueTextSize = 12f // Ukuran teks nilai

        val pieData = PieData(dataSet)

        val pieChart = binding.pieChart
        pieChart.data = pieData
        pieChart.invalidate()
    }
    private fun columnChartData() {
        val entries = listOf(
            BarEntry(0f, 98f),   // Data Fatur dengan nilai 98
            BarEntry(1f, 100f),  // Data Wahyu dengan nilai 100
            BarEntry(2f, 78f)    // Data Ahmad dengan nilai 78
        )

        val dataSet = BarDataSet(entries, "Mahasiswa Berprestasi")
        dataSet.colors = mutableListOf(Color.RED, Color.GREEN, Color.BLUE) // Warna untuk setiap bar
        dataSet.valueTextSize = 12f // Ukuran teks nilai

        val barData = BarData(dataSet)

        val barChart = binding.barChart
        barChart.data = barData
        barChart.setFitBars(true)
        barChart.invalidate()
    }
}