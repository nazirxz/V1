package com.example.suksesdidikan

data class Rekomen (
    val avatar: Int, // URL avatar (gambar)
    val matapelajaran: String // Nama
)

// Contoh data dummy
val dummyList: List<Rekomen> = listOf(
    Rekomen(R.drawable.agama, "Agama"),
    Rekomen(R.drawable.bing, "Bahasa Inggris"),
    Rekomen(R.drawable.ppkn,"PPKN")
)