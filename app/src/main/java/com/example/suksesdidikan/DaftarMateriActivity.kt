package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.databinding.ActivityDaftarmatapelajaranBinding
import java.io.Serializable

class DaftarMateriActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDaftarmatapelajaranBinding
    private lateinit var daftarMatpelAdapter: DaftarMatpelAdapter
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
        // Initialize the adapter with the dummyList
        daftarMatpelAdapter = DaftarMatpelAdapter(dummyList)

        // Set the adapter to the RecyclerView
        binding.rvMataPelajaran.adapter = daftarMatpelAdapter

        // Set layout manager to the RecyclerView (e.g., LinearLayoutManager)
        binding.rvMataPelajaran.layoutManager = LinearLayoutManager(this)
        daftarMatpelAdapter.setOnItemClickListener(object : DaftarMatpelAdapter.OnItemClickListener {
            override fun onItemClick(buku: Buku) {
                // Serialize isiBab map into intent extras
                val intent = Intent(this@DaftarMateriActivity, DaftarBabActivity::class.java)
                val serializableExtra = buku.isiBab as Serializable
                intent.putExtra("ISI_BAB_MAP", serializableExtra)
                // Tambahkan informasi tentang mata pelajaran ke intent
                intent.putExtra("MAPELAJARAN", buku.matapelajaran)
                startActivity(intent)
            }
        })
    }
}