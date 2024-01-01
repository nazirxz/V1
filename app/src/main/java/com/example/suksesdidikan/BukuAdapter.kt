package com.example.suksesdidikan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemRekomenBinding
class BukuAdapter(private var data: List<Buku>, private val onItemClick: (Buku) -> Unit) :
    RecyclerView.Adapter<BukuAdapter.ViewHolder>() {

    // Fungsi untuk memperbarui data di dalam adapter
    fun updateList(newList: List<Buku>) {
        data = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRekomenBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemRekomenBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item:Buku) {
            binding.tvMatpel.text = item.matapelajaran
            binding.tvAvatar.setImageResource(item.avatar)
        }
    }
}
