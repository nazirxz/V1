package com.example.suksesdidikan.model

import java.io.Serializable

data class Buku(
    val gambar: Int,
    val matapelajaran: String,
    val kelas: String,
    val deskripsi: String,
    val bab: List<BabInfo>
): Serializable