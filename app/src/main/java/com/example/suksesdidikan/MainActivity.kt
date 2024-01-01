package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suksesdidikan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("USER_NAME")
        val welcomeText = "Hello $userName"
        binding.tvWelcome.text = welcomeText

        // Mengatur klik listener pada tombol drawer
        binding.drawer.setOnClickListener {
            // Menampilkan atau menyembunyikan navigation drawer sesuai status saat ini
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawer(binding.navigationView)
            } else {
                binding.drawerLayout.openDrawer(binding.navigationView)
            }
        }

        //filter data kelas
        binding.btn7.setOnClickListener {
            filterByKelas("VII")
        }

        binding.btn8.setOnClickListener {
            filterByKelas("VIII")
        }

        binding.btn9.setOnClickListener {
            filterByKelas("IX")
        }


        binding.bottomNavigation.selectedItemId = R.id.bottom_home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    true
                }
                R.id.bottom_materi -> {
                    val intent = Intent(this@MainActivity, DaftarMateriActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.bottom_result -> {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    finish()
                    true
                }
                // Tambahkan case untuk item lain jika diperlukan
                else -> false
            }
        }


        // Membuat adapter dan mengatur ke RecyclerView
        val adapter = RekomenAdapter(dummyList) { selectedItem ->
            // Lakukan apa pun yang ingin Anda lakukan ketika item diklik di sini
            // Contohnya, menuju ke halaman lain dengan data yang dipilih
            val intent = Intent(this, KursusActivity::class.java)
            intent.putExtra("USER_NAME", userName)
            intent.putRekomenExtra(selectedItem)
//            intent.putExtra("selected_item", selectedItem)
            startActivity(intent)
        }

        // Mengatur LayoutManager untuk RecyclerView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRekomen.layoutManager = layoutManager
        binding.rvRekomen.adapter = adapter

    }
    private fun filterByKelas(kelas: String) {
        val filteredList = dummyList.filter { it.kelas == kelas }
        (binding.rvRekomen.adapter as? RekomenAdapter)?.updateList(filteredList)
    }
    fun Intent.putRekomenExtra(rekomen: Rekomen) {
        with(rekomen) {
            putExtra("AVATAR", avatar)
            putExtra("MAPELAJARAN", matapelajaran)
            putExtra("DESKRIPSI", deskripsi)
        }
    }
}