package com.example.suksesdidikan

data class Buku(
    val avatar: Int, // URL avatar (gambar)
    val matapelajaran: String, // Nama
    val kelas: String,
    val deskripsi:String
)

// Contoh data dummy
val dummyList: List<Buku> = listOf(
    Buku(R.drawable.agama, "Agama", "IX","Tim kami sebagian mengambil tugas agama"),
    Buku(R.drawable.bing, "Bahasa Inggris", "VII","Tim kami sebagian mengambil tugas bahasa inggris"),
    Buku(R.drawable.ppkn,"PPKN","VIII","Tim kami sebagian mengambil tugas PPKN")
)