package com.example.suksesdidikan

import Buku
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.DummyData.dummyList
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
        val userId = intent.getStringExtra("USER_ID")
        binding.bottomNavigation.selectedItemId = R.id.bottom_materi
        binding.bottomNavigation.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@DaftarMateriActivity,MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    intent.putExtra("USER_ID",userId)

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
                    intent.putExtra("USER_ID",userId)

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
                val intent = Intent(this@DaftarMateriActivity, DaftarBabActivity::class.java)

                // Buat ArrayList dari semua detail bab
                val babDetails = ArrayList<BabDetail>()
                for (babInfo in buku.bab) {
                    babDetails.addAll(babInfo.isi)
                }

                // Kirim ArrayList babDetails dan mata pelajaran ke activity berikutnya
                intent.putExtra("BAB_DETAILS", babDetails as Serializable)
                intent.putExtra("MAPELAJARAN", buku.matapelajaran)
                intent.putExtra("USER_NAME", userName)
                intent.putExtra("USER_ID",userId)

                startActivity(intent)
            }
        })
    }
    override fun onBackPressed() {
        AlertDialog.Builder(this@DaftarMateriActivity, R.style.AlertDialogTheme)
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
}