package com.example.suksesdidikan

import Buku
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.DummyData.dummyList
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
        val userId = intent.getStringExtra("USER_ID")
        val userName = intent.getStringExtra("USER_NAME")
        val kelas = intent.getStringExtra("USER_KELAS")
        val usia = intent.getStringExtra("USER_USIA")
        // Gunakan data yang diambil kembali untuk menampilkan informasi di layout activity_kursus
        binding.tvMatapelajaran.text = matapelajaran
        binding.tvDescription.text = deskripsi
        binding.avatar.setImageResource(avatar)
        binding.tvKursus.text = matapelajaran

        // Handle bottom navigation
        binding.bottomNavigation.selectedItemId = R.id.bottom_home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@KursusActivity, MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)
                    intent.putExtra("USER_KELAS",kelas)
                    intent.putExtra("USER_USIA",usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> {
                    val intent = Intent(this@KursusActivity, DaftarMateriActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)
                    intent.putExtra("USER_KELAS",kelas)
                    intent.putExtra("USER_USIA",usia)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_result -> {
                    val intent = Intent(this@KursusActivity, ResultActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)
                    intent.putExtra("USER_KELAS",kelas)
                    intent.putExtra("USER_USIA",usia)
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
        val selectedBabList = getSelectedBabList(dummyList, matapelajaran ?: "")
        val adapter = BabAdapter(selectedBabList)
        binding.rvBab.layoutManager = LinearLayoutManager(this)
        binding.rvBab.adapter = adapter

        adapter.setOnItemClickListener { babInfo ->
            val intent = Intent(this@KursusActivity, DaftarBabActivity::class.java)
            intent.putExtra("USER_NAME", userName)
            intent.putExtra("MAPELAJARAN", matapelajaran)
            intent.putExtra("USER_ID",userId)
            intent.putExtra("USER_KELAS",kelas)
            intent.putExtra("USER_USIA",usia)
            // Add more data if needed
            startActivity(intent)
        }
    }

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
}
