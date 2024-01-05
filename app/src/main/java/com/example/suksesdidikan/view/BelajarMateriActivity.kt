package com.example.suksesdidikan.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.R
import com.example.suksesdidikan.databinding.ActivityBelajarBinding

class BelajarMateriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBelajarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data dari intent yang dikirimkan dari aktivitas sebelumnya
        val isiBab = intent.getStringExtra("ISI_BAB")
        val userId = intent.getStringExtra("USER_ID")
        val namaBab = intent.getStringExtra("NAMA_BAB")
        val bab = intent.getStringExtra("BAB")
        val userName = intent.getStringExtra("USER_NAME")
        val kelas = intent.getStringExtra("USER_KELAS")
        val usia = intent.getStringExtra("USER_USIA")

        // Menampilkan data pada tampilan menggunakan data dari intent
        binding.bab.text = bab
        binding.judulBab.text = namaBab
        binding.isiBab.text = isiBab

        // Menentukan tindakan saat item di bottom navigation dipilih
        binding.bottomNavigation.selectedItemId = R.id.bottom_materi
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    // Pindah ke MainActivity dengan membawa data pengguna yang diperlukan
                    val intent = Intent(this@BelajarMateriActivity, MainActivity::class.java)
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
                    val intent = Intent(this@BelajarMateriActivity, DaftarMateriActivity::class.java)
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
                    val intent = Intent(this@BelajarMateriActivity, ResultActivity::class.java)
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

        // Tombol kembali ke aktivitas sebelumnya
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}