package com.example.suksesdidikan.model
data class User(
    val fullName: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val usia: String="",
    val kelas: String = "",
    val userId: String= ""// Menyimpan timestamp terakhir aktivitas pengguna
)
