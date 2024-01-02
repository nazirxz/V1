package com.example.suksesdidikan

import java.io.Serializable

data class BabDetail(
    val judul: String,
    val isi: String,
    val gambar: Int
): Serializable