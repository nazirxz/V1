package com.example.suksesdidikan.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflasi layout menggunakan ViewBinding
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Atur onClickListener untuk tombol masuk untuk navigasi ke SignInActivity
        binding.signIn2.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        // Atur onClickListener untuk tombol daftar untuk navigasi ke SignUpActivity
        binding.daftar.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}