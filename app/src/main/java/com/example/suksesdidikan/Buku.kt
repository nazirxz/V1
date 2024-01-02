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
    ),
    Buku(
        R.drawable.bing,
        "Bahasa Inggris",
        "VII",
        "Deskripsi Bahasa Inggris",
        mapOf(
            "Bab1 Bing" to Bab("BAB 1 Congratulation", "Ini adalah isi",R.drawable.bing),
            "Bab2 Bing" to Bab("BAB 2 Expression","Ini adalah isi", R.drawable.bing),
            "Bab3 Bing" to Bab("BAB 3 Greetings" ,"Ini adalah isi",R.drawable.bing)
        )
    ),
    Buku(
        R.drawable.ppkn,
        "PPKN",
        "VII",
        "Deskripsi Bahasa Inggris",
        mapOf(
            "Bab1 PPKN" to Bab("BAB 1 Pancasila", "Ini adalah isi",R.drawable.ppkn),
            "Bab2 PPKN" to Bab("BAB 2 Pancasila","Ini adalah isi", R.drawable.ppkn),
            "Bab3 PPKN" to Bab("BAB 3 NKRI" ,"Ini adalah isi",R.drawable.ppkn)
        )
    ),
)