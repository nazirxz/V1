package com.example.suksesdidikan

data class Buku(
    val avatar: Int,
    val matapelajaran: String,
    val kelas: String,
    val deskripsi: String,
    val isiBab: Map<String, Bab> // Map untuk menyimpan konten dan gambar berdasarkan bab
)

val dummyList: List<Buku> = listOf(
    Buku(
        R.drawable.agama,
        "Agama",
        "IX",
        "Deskripsi Agama",
        mapOf(
            "Bab1" to Bab("BAB 1 Islam", "Ini adalah isi",R.drawable.agama),
            "Bab2" to Bab("BAB 2 Pilar Agama","Ini adalah isi", R.drawable.agama),
            "Bab3" to Bab("BAB 3 Islam" ,"Ini adalah isi",R.drawable.agama)
        )
    )
)