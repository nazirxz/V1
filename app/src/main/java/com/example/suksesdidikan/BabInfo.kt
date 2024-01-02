package com.example.suksesdidikan

import java.io.Serializable

data class BabInfo(
    val bab: String,
    val isi: List<BabDetail>
): Serializable