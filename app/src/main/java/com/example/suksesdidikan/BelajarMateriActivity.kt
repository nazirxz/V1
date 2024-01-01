package com.example.suksesdidikan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivityBelajarBinding

class BelajarMateriActivity:AppCompatActivity() {
    private lateinit var binding: ActivityBelajarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isiBab = intent.getStringExtra("ISI_BAB")
        val namaBab = intent.getStringExtra("NAMA_BAB")

        binding.bab.text = namaBab
        binding.isiBab.text = isiBab
        // Gunakan isiBab dan namaBab sesuai kebutuhan di sini
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}