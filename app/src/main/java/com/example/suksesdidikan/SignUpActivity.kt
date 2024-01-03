package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivitySignupBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }

        binding.signIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            val fullName = binding.tvFullname.text.toString().trim()
            val phone = binding.tvPhone.text.toString().trim()
            val email = binding.tvEmail.text.toString().trim()
            val password = binding.tvPwd.text.toString().trim()

            if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
            ) {
                Toast.makeText(this, "Semua isian harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                // Simpan data pengguna ke Firebase Realtime Database
                saveUserToDatabase(fullName, phone, email, password)
            }
        }
    }

    private fun saveUserToDatabase(fullName: String, phone: String, email: String, password: String) {
        val userId = database.child("users").push().key
        val user = User(fullName, phone, email, password, 0,userId ?: "")


        if (userId != null) {
            database.child("users").child(userId).setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("USER_ID", userId) // Mengirim userId sebagai extra
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Gagal membuat ID pengguna!", Toast.LENGTH_SHORT).show()
        }
    }
}