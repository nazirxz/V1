package com.example.suksesdidikan.adapter

import com.example.suksesdidikan.model.Buku
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemMateriBinding

class DaftarMatpelAdapter(private val matpelList: List<Buku>) :
    RecyclerView.Adapter<DaftarMatpelAdapter.MatpelViewHolder>() {

    // Mendefinisikan sebuah antarmuka untuk listener
    interface OnItemClickListener {
        fun onItemClick(buku: Buku)
    }

    private var itemClickListener: OnItemClickListener? = null

    // Fungsi untuk mengatur listener saat item diklik
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    // Membuat MatpelViewHolder berdasarkan tampilan item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatpelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMateriBinding.inflate(inflater, parent, false) // Menggunakan DataBinding untuk tampilan item
        return MatpelViewHolder(binding)
    }

    // Mengikat data dari Buku ke tampilan pada ViewHolder
    override fun onBindViewHolder(holder: MatpelViewHolder, position: Int) {
        val matpel = matpelList[position]
        holder.bind(matpel)

        // Menangani klik pada item
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(matpel) // Memanggil fungsi saat item diklik
        }
    }

    // Mendapatkan jumlah total item dalam daftar
    override fun getItemCount(): Int {
        return matpelList.size
    }

    // Kelas ViewHolder untuk menangani tampilan setiap item
    class MatpelViewHolder(private val binding: ItemMateriBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Mengikat data Buku ke tampilan item pada ViewHolder
        fun bind(matpel: Buku) {
            binding.materi.text = matpel.matapelajaran // Mengatur teks pada tampilan item dengan nama mata pelajaran
            binding.avatar.setImageResource(matpel.gambar) // Mengatur gambar pada tampilan item
            binding.description.text = matpel.deskripsi // Mengatur teks deskripsi pada tampilan item
        }
    }
}