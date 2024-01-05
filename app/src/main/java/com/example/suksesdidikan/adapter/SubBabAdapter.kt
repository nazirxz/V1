package com.example.suksesdidikan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemSubbabBinding
import com.example.suksesdidikan.model.BabDetail

class SubBabAdapter(private val list: List<BabDetail>) :
    RecyclerView.Adapter<SubBabAdapter.ChildViewHolder>() {

    // Listener untuk menangani klik pada item
    private var onItemClickListener: ((BabDetail) -> Unit)? = null

    // Membuat ChildViewHolder berdasarkan tampilan item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubbabBinding.inflate(inflater, parent, false) // Menggunakan DataBinding untuk tampilan item
        return ChildViewHolder(binding)
    }

    // Mengikat data dari BabDetail ke tampilan pada ViewHolder
    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val babDetail = list[position]
        holder.bind(babDetail)
    }

    // Mendapatkan jumlah total item dalam daftar
    override fun getItemCount(): Int {
        return list.size
    }

    inner class ChildViewHolder(private val binding: ItemSubbabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Mengikat data BabDetail ke tampilan pada ViewHolder
        fun bind(babDetail: BabDetail) {
            binding.subbab.text = babDetail.judul // Mengatur teks pada tampilan item dengan judul BabDetail

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(babDetail) // Menangani klik pada item dan memicu aksi yang sesuai
            }
        }
    }

    // Fungsi untuk mengatur listener saat item diklik
    fun setOnItemClickListener(listener: (BabDetail) -> Unit) {
        onItemClickListener = listener
    }
}

