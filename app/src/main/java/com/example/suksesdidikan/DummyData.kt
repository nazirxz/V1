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
       Buku(
           R.drawable.bing,
           "Bahasa Inggris",
           "VII",
           "Deskripsi Bahasa Inggris",
           listOf(
               BabInfo(
                   "Bab 1",
                   listOf(
                       BabDetail("Greeting", "Ini adalah isi", 0),
                       BabDetail("Expression", "Ini adalah isi", 0)
                   )
               ),
               BabInfo(
                   "Bab 2",
                   listOf(
                       BabDetail("Grammar", "Ini adalah isi", 0),
                       BabDetail("Attention", "Ini adalah isi", 0)
                   )
               )
           )
       ),
        // ... dan seterusnya untuk buku-buku lainnya
    )
    // Mendapatkan semua BabInfo dari semua Buku
    fun getAllBabInfoFromBooks(): List<BabInfo> {
        val allBabInfos = mutableListOf<BabInfo>()

        // Iterate melalui setiap Buku dan tambahkan semua BabInfo ke dalam list
        dummyList.forEach { buku ->
            allBabInfos.addAll(buku.bab)
        }

        return allBabInfos
    }
}