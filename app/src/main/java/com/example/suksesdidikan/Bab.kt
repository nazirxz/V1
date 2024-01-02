package com.example.suksesdidikan

import java.io.Serializable

data class Bab(
    val namaBab: String,
    val isi: String,
    val gambar: Int
) : Serializable