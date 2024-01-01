package com.example.suksesdidikan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemRekomenBinding
class RekomenAdapter(private var data: List<Rekomen>, private val onItemClick: (Rekomen) -> Unit) :
    RecyclerView.Adapter<RekomenAdapter.ViewHolder>() {

    // Fungsi untuk memperbarui data di dalam adapter
    fun updateList(newList: List<Rekomen>) {
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

        fun bind(item: Rekomen) {
            binding.tvMatpel.text = item.matapelajaran
            binding.tvAvatar.setImageResource(item.avatar)
        }
    }
}
