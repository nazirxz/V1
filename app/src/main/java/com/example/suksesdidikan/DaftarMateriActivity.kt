package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivityDaftarmatapelajaranBinding

class DaftarMateriActivity: AppCompatActivity() {
    private lateinit var binding:ActivityDaftarmatapelajaranBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarmatapelajaranBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userName = intent.getStringExtra("USER_NAME")
        binding.bottomNavigation.selectedItemId = R.id.bottom_materi
        binding.bottomNavigation.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@DaftarMateriActivity,MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> {
                    true
                }
                R.id.bottom_result -> {
                    val intent = Intent(this@DaftarMateriActivity, ResultActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    finish()
                    true
                }
                // Tambahkan case untuk item lain jika diperlukan
                else -> false
            }
        }
    }
}