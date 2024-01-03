package com.example.suksesdidikan
data class User(
    val userId: String = "",
    val fullName: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val lastActiveTimestamp: Long = 0 // Menyimpan timestamp terakhir aktivitas pengguna
)
