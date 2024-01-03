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
           "IX",
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
       Buku(
           R.drawable.mtk,
           "Matematika",
           "VII",
           "Matematika adalah ilmu yang mempelajari hal-hal seperti besaran, struktur, ruang dan perubahan",
           listOf(
               BabInfo(
                   "Bab 1 Trigonometri",
                   listOf(
                       BabDetail("1.1 Pengukuran Sudut", "Berdasarkan gambar di atas dapat kita simpulkan bahwa pengukuran sudut merupakan salah satu aspek penting dalam pengukuran dan pemetaan kerangka maupun titik-titik detail. Sistem besaran sudut yang dipakai juga berbeda antara satu dengan yang lainnya. Sistem besaran sudut pada pengukuran dan pemetaan dapat terdiri dari:\n" +
                               "\n" +
                               "Sistem Besaran Sudut Seksagesimal\n" +
                               "Sistem Besaran Sudut Sentisimal\n" +
                               "Sistem Sesaran Sudut Radian\n" +
                               "Dasar untuk mengukur besaran sudutnya seperti suatu lingkaran yang dibagi menjadi empat bagian, yang dinamakan kuadran yaitu Kudran I, II, III dan kuadran IV.\n" +
                               "\n" +
                               "Untuk cara sexagesimal lingkaran dapat dibagi menjadi 360 bagian yang sama dan tiap bagiannya disebut derajat. Maka 1 kuadran dalam lingkaran tersebut = 900.\n" +
                               "\n" +
                               "1o = 60’ 1’ = 60” 1o = 3600”", R.drawable.mtkg11),
                       BabDetail("Contoh Soal", "1. Ubahlah sudut-sudut berikut ini kedalam suatu radian!\n" +
                               "(a) 30°\n" +
                               "(b) 120°\n" +
                               "(c) 225°\n" +
                               "\n" +
                               "Jawaban\n" +
                               "(a) 30° = 30∘180∘πrad=16πrad\n" +
                               "(b) 120° = 120∘180∘πrad=23πrad\n" +
                               "(c) 225° = 225∘180∘πrad=54πrad\n" +
                               "\n" +
                               "2. Tentukanlah nilai dari sin sin 120°\n" +
                               "\n" +
                               "Jawaban \n" +
                               "(a) sin sin 120° = sin sin (90°+30°) = cos cos 30° = 123–√\n" +
                               "\n" +
                               "3. Diketahui segitiga ABC siku-siku di B, dimana AB = 12 cm dan AC = 4 cm. Tentukanlah nilai Cos a?\n" +
                               "BC 16−2−−−−−√=4–√=2\n" +
                               "cos cos A = ABAC=3√2\n" +
                               "\n" +
                               "4. Diketahui segitiga ABC siku-siku di B dan besar sudut C adalah 60°. Jika panjang AC = 12 cm, maka tentukanlah panjang:\n" +
                               "(a) AB\n" +
                               "(b) BC\n" +
                               "\n" +
                               "(a) sin sin 60° = ACBC\n" +
                               "3√2=AB12\n" +
                               "AB = 12 ×3√2\n" +
                               "AB = 63–√\n" +
                               "\n" +
                               "(b) cos cos 60° = BCAC\n" +
                               "12=AB12\n" +
                               "AB = 12×12\n" +
                               "AB = 6\n" +
                               "\n" +
                               "5. Seseorang melihat puncak menara dari suatu tempat dengan sudut elevasi 60°. Jika diketahui tinggi menara adalah 90 m maka tentukanlah jarak orang tersebut ke kaki menara (tinggi orang diabaikan)\n" +
                               "\n" +
                               "posisi orang adalah A\n" +
                               "\n" +
                               "Jarak orang ke menara = AB\n" +
                               "\n" +
                               "tan tan 60° = BCAB\n" +
                               "\n" +
                               "3–√=90AB\n" +
                               "AB = 303–√", 0)
                   )
               )
           )
       ),
        // ... dan seterusnya untuk buku-buku lainnya
    )
    // Mendapatkan semua BabInfo dari semua Buku
    fun getAllBabInfoFromBooks(matapelajaran: String): List<BabInfo> {
        val allBabInfos = mutableListOf<BabInfo>()

        // Iterate melalui setiap Buku dan tambahkan semua BabInfo yang sesuai ke dalam list
        dummyList.forEach { buku ->
            if (buku.matapelajaran.equals(matapelajaran, ignoreCase = true)) {
                allBabInfos.addAll(buku.bab)
            }
        }

        return allBabInfos
    }
}