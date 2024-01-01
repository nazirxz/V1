package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.databinding.ActivityKursusBinding

class KursusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKursusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKursusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent yang dikirim
        val avatar = intent.getIntExtra("AVATAR", 0)
        val matapelajaran = intent.getStringExtra("MAPELAJARAN")
        val deskripsi = intent.getStringExtra("DESKRIPSI")
        val userName = intent.getStringExtra("USER_NAME")

        // Gunakan data yang diambil kembali untuk menampilkan informasi di layout activity_kursus
        binding.tvMatapelajaran.text = matapelajaran
        binding.tvDescription.text = deskripsi
        binding.avatar.setImageResource(avatar)
        binding.tvKursus.text = matapelajaran

        // Handle bottom navigation
        binding.bottomNavigation.selectedItemId = R.id.bottom_materi
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@KursusActivity, MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> {
                    // No action needed as already on the same screen
                    true
                }
                R.id.bottom_result -> {
                    val intent = Intent(this@KursusActivity, ResultActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
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
        val selectedBabList = getSelectedBabList(dummyList)
        val adapter = BabAdapter(selectedBabList)
        binding.rvBab.layoutManager = LinearLayoutManager(this)
        binding.rvBab.adapter = adapter
    }

    fun getSelectedBabList(data: List<Buku>): List<Bab> {
        val selectedBabList = mutableListOf<Bab>()
        for (buku in data) {
            buku.isiBab["Bab1"]?.let { selectedBabList.add(it) }
            buku.isiBab["Bab2"]?.let { selectedBabList.add(it) }
            buku.isiBab["Bab3"]?.let { selectedBabList.add(it) }
        }
        return selectedBabList
    }
}
