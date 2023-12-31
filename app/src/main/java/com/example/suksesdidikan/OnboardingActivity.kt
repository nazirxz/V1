package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivityOnboardingBinding

class OnboardingActivity: AppCompatActivity() {
    private lateinit var binding:ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signIn2.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.daftar.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}