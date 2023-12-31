package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivitySignupBinding

class SignUpActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,OnboardingActivity::class.java))
            finish()
        }
        binding.signIn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }
    }
}