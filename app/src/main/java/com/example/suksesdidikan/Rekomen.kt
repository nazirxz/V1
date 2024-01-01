package com.example.suksesdidikan

data class Rekomen (
    val avatar: Int, // URL avatar (gambar)
    val matapelajaran: String, // Nama
    val kelas: String,
    val deskripsi:String
)

// Contoh data dummy
val dummyList: List<Rekomen> = listOf(
    Rekomen(R.drawable.agama, "Agama", "IX","Tim kami sebagian mengambil tugas agama"),
    Rekomen(R.drawable.bing, "Bahasa Inggris", "VII","Tim kami sebagian mengambil tugas bahasa inggris"),
    Rekomen(R.drawable.ppkn,"PPKN","VIII","Tim kami sebagian mengambil tugas PPKN")
)