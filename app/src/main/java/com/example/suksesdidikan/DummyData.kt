package com.example.suksesdidikan

import Buku

object DummyData {
    val dummyList: List<Buku> = listOf(
        Buku(
            R.drawable.agama,
            "Agama",
            "IX",
            "Deskripsi Agama",
            listOf(
                BabInfo(
                    "Bab 1",
                    listOf(
                        BabDetail("Pilar Islam", "Ini adalah isi", 0),
                        BabDetail("Pilar Agama", "Ini adalah isi", 0)
                    )
                ),
                BabInfo(
                    "Bab 2",
                    listOf(
                        BabDetail("Apa itu Islam", "Ini adalah isi", 0),
                        BabDetail("Nabi Islam", "Ini adalah isi", 0)
                    )
                )
            )
        ),
        // ... dan seterusnya untuk buku-buku lainnya
    )
}