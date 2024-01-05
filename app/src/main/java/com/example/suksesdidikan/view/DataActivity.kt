package com.example.suksesdidikan.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.R
import com.example.suksesdidikan.databinding.ActivityDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi DatabaseReference
        database = FirebaseDatabase.getInstance().reference

        // Inisialisasi Spinner
        val spinner = binding.spinnerKelas
        val userId = intent.getStringExtra("USER_ID")
        val userName = intent.getStringExtra("USER_NAME")

        // Atur adapter untuk Spinner dengan daftar opsi
        ArrayAdapter.createFromResource(
            this,
            R.array.kelas_array, // Pastikan array kelas_array sudah didefinisikan di strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Simpan perubahan saat tombol Save ditekan
        binding.btnSignUp.setOnClickListener {
            val usia = binding.tvAge.text.toString()
            val kelas = binding.spinnerKelas.selectedItem // Dapatkan indeks pilihan Spinner

            if (usia.isNotEmpty() && kelas != null) { // Pastikan usia terisi dan kelas dipilih
                val userUpdateMap = HashMap<String, Any>()
                userUpdateMap["usia"] = usia
                userUpdateMap["kelas"] = kelas.toString()

                // Perbarui data usia dan kelas di Firebase Database
                database.child("users").child(userId!!).updateChildren(userUpdateMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Jika berhasil, pindah ke MainActivity dengan data yang diperlukan
                            Toast.makeText(this, "Data usia dan kelas berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("USER_NAME", userName)
                            intent.putExtra("USER_USIA", usia)
                            intent.putExtra("USER_KELAS", kelas.toString())
                            intent.putExtra("USER_ID", userId) // Mengirim userId sebagai extra
                            startActivity(intent)
                        } else {
                            // Jika gagal, tampilkan pesan kesalahan
                            Toast.makeText(this, "Gagal memperbarui data usia dan kelas: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Jika usia atau kelas belum diisi, tampilkan pesan
                Toast.makeText(this, "Mohon isi usia dan pilih kelas!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
