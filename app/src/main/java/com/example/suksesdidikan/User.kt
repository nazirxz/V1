package com.example.suksesdidikan
data class User(
    val fullName: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val usia: Int = 0,
    val kelas: String = "",
    val userId: String= ""// Menyimpan timestamp terakhir aktivitas pengguna
)
