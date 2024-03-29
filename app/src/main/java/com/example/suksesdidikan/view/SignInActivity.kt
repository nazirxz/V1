package com.example.suksesdidikan.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivitySigninBinding
import com.example.suksesdidikan.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi DatabaseReference
        database = FirebaseDatabase.getInstance().reference

        // Tombol kembali ke OnboardingActivity
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }

        // Pergi ke SignUpActivity
        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        // Tombol Sign In
        binding.btnSignIn.setOnClickListener {
            val email = binding.tvEmail.text.toString().trim()
            val password = binding.tvPwd.text.toString().trim()

            // Memastikan email dan password terisi
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Email dan password harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                // Lakukan pengecekan di Firebase Realtime Database
                checkUserLogin(email, password)
            }
        }
    }

    // Fungsi untuk memeriksa login pengguna di Firebase
    private fun checkUserLogin(email: String, password: String) {
        val userRef = database.child("users")

        userRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)

                            // Mendapatkan userId dari snapshot
                            val userId = userSnapshot.key

                            val userName = user?.fullName ?: "User"
                            val kelas = user?.kelas?: "User"
                            val usia = user?.usia?: "User"

                            if (user != null && user.password == password) {
                                // Login berhasil
                                Toast.makeText(this@SignInActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()

                                // Menyiapkan intent dengan informasi pengguna
                                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                intent.putExtra("USER_NAME", userName)
                                intent.putExtra("USER_USIA",usia)
                                intent.putExtra("USER_KELAS",kelas)
                                intent.putExtra("USER_ID", userId) // Mengirim userId sebagai extra

                                // Beralih ke MainActivity
                                startActivity(intent)
                                finish()
                                return
                            }
                        }
                    }
                    // Jika tidak ada data pengguna atau password salah
                    Toast.makeText(this@SignInActivity, "Email atau password salah!", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@SignInActivity, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}