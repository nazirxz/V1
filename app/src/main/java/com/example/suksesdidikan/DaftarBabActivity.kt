package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.DummyData.dummyList
import com.example.suksesdidikan.databinding.ActivityDaftarbabBinding

class DaftarBabActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarbabBinding
    private lateinit var daftarBabAdapter: DaftarBabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarbabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")
        val matapelajaran = intent.getStringExtra("MAPELAJARAN") ?: ""

        // Get the filtered list of BabInfo based on the selected mata pelajaran
        val filteredBabInfoList = DummyData.getAllBabInfoFromBooks(matapelajaran)

        // Set adapter with the filtered list of BabInfo and the full list of Buku
        daftarBabAdapter = DaftarBabAdapter(filteredBabInfoList, DummyData.dummyList)
        binding.rvBab.adapter = daftarBabAdapter
        binding.rvBab.layoutManager = LinearLayoutManager(this)

        binding.bottomNavigation.selectedItemId = R.id.bottom_materi
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@DaftarBabActivity, MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi ->{
                    val intent = Intent(this@DaftarBabActivity, DaftarMateriActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)

                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_result -> {
                    val intent = Intent(this@DaftarBabActivity, ResultActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)

                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
        daftarBabAdapter.setOnItemClickListener { babDetail, babInfo ->
            val intent = Intent(this, BelajarMateriActivity::class.java)
            intent.putExtra("ISI_BAB", babDetail.isi)
            intent.putExtra("NAMA_BAB", babDetail.judul)
            intent.putExtra("BAB", babInfo.bab)
            intent.putExtra("USER_NAME", userName)
            intent.putExtra("USER_ID",userId)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}