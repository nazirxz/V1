package com.example.suksesdidikan.model

import java.io.Serializable

data class BabDetail(
    val judul: String,
    val isi: String,
    val gambar: Int
): Serializable