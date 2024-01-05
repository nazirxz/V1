package com.example.suksesdidikan.adapter

import com.example.suksesdidikan.model.Buku
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemRekomenBinding
class BukuAdapter(private var data: List<Buku>, private val onItemClick: (Buku) -> Unit) :
    RecyclerView.Adapter<BukuAdapter.ViewHolder>() {

    // Fungsi untuk memperbarui data di dalam adapter
    fun updateList(newList: List<Buku>) {
        data = newList
        notifyDataSetChanged() // Memberitahu RecyclerView bahwa ada perubahan data
    }

    // Membuat ViewHolder baru sesuai dengan tampilan item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRekomenBinding.inflate(inflater, parent, false) // Membuat tampilan item menggunakan DataBinding
        return ViewHolder(binding) // Mengembalikan ViewHolder baru dengan tampilan item yang dibuat
    }

    // Menghubungkan data dari list dengan tampilan pada ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position] // Mengambil data Buku berdasarkan posisi
        holder.bind(item) // Memasukkan data Buku ke dalam ViewHolder
        holder.itemView.setOnClickListener { onItemClick(item) } // Menangani klik pada item dan memicu aksi yang sesuai
    }

    // Mendapatkan jumlah total item dalam daftar
    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemRekomenBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            // Inisialisasi item click di sini
            itemView.setOnClickListener { onItemClick(data[adapterPosition]) } // Menangani klik pada item dan memicu aksi yang sesuai
        }

        // Mengikat data Buku ke tampilan item pada ViewHolder
        fun bind(item: Buku) {
            binding.tvMatpel.text = item.matapelajaran // Mengatur teks pada tampilan item dengan data Buku
            binding.tvAvatar.setImageResource(item.gambar) // Mengatur gambar pada tampilan item dengan data Buku
        }
    }
}
