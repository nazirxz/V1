package com.example.suksesdidikan.model

import java.io.Serializable

data class BabInfo(
    val bab: String,
    val isi: List<BabDetail>
): Serializable