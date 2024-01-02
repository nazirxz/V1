package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.databinding.ActivityDaftarbabBinding

class DaftarBabActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarbabBinding
    private lateinit var daftarBabAdapter: DaftarBabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarbabBinding.inflate(layoutInflater)
        setContentView(binding.root)


            // Set initial adapter with the full list
        daftarBabAdapter = DaftarBabAdapter(dummyList)
        binding.rvBab.adapter = daftarBabAdapter
        binding.rvBab.layoutManager = LinearLayoutManager(this)

        val userName = intent.getStringExtra("USER_NAME")
        binding.bottomNavigation.selectedItemId = R.id.bottom_materi
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@DaftarBabActivity, MainActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_materi -> true
                R.id.bottom_result -> {
                    val intent = Intent(this@DaftarBabActivity, ResultActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        // Get the selected mata pelajaran from the intent
        val matapelajaran = intent.getStringExtra("MAPELAJARAN")

        // Filter the list based on the selected mata pelajaran
        val filteredList = dummyList.filter { it.matapelajaran.equals(matapelajaran, ignoreCase = true) }

        // Update the adapter with the filtered list
        daftarBabAdapter = DaftarBabAdapter(filteredList)
        binding.rvBab.adapter = daftarBabAdapter
    }
}