package com.example.suksesdidikan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suksesdidikan.databinding.ActivityKursusBinding

class KursusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKursusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKursusBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}