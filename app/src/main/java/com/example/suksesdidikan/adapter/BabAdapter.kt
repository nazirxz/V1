package com.example.suksesdidikan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemBabBinding
import com.example.suksesdidikan.model.BabInfo

class BabAdapter(private val babInfoList: List<BabInfo>) : // Kelas adaptor untuk RecyclerView dengan daftar BabInfo sebagai input

    RecyclerView.Adapter<BabAdapter.BabInfoViewHolder>() { // Menggunakan RecyclerView Adapter dengan ViewHolder kustom

    private var onItemClick: ((BabInfo) -> Unit)? = null // Menyimpan aksi yang akan dipicu saat item diklik

    // Fungsi untuk mengatur listener saat item diklik
    fun setOnItemClickListener(listener: (BabInfo) -> Unit) {
        onItemClick = listener
    }

    // Membuat ViewHolder baru sesuai dengan tampilan item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BabInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBabBinding.inflate(inflater, parent, false) // Membuat tampilan item menggunakan DataBinding
        return BabInfoViewHolder(binding) // Mengembalikan ViewHolder baru dengan tampilan item yang dibuat
    }

    // Menghubungkan data dari list dengan tampilan pada ViewHolder
    override fun onBindViewHolder(holder: BabInfoViewHolder, position: Int) {
        val babInfo = babInfoList[position] // Mengambil data BabInfo berdasarkan posisi
        holder.bind(babInfo) // Memasukkan data BabInfo ke dalam ViewHolder
    }

    // Mendapatkan jumlah total item dalam daftar
    override fun getItemCount(): Int = babInfoList.size

    inner class BabInfoViewHolder(private val binding: ItemBabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Mengikat data BabInfo ke tampilan item pada ViewHolder
        fun bind(babInfo: BabInfo) {
            binding.kursus.text = babInfo.bab // Mengatur teks pada tampilan item dengan data BabInfo

            // Menangani klik pada item dan memicu aksi yang sesuai
            binding.root.setOnClickListener {
                onItemClick?.invoke(babInfo)
            }
        }
    }
}