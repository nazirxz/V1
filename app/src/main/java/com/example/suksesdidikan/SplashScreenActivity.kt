package com.example.suksesdidikan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivitySplashscreenBinding

class SplashScreenActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            FULL_SCREEN_FLAG,
            FULL_SCREEN_FLAG
        )
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }, TIME)
    }

    companion object {
        private const val TIME = 3000L
        private const val FULL_SCREEN_FLAG = 1024
    }
}