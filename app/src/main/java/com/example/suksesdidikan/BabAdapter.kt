package com.example.suksesdidikan

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemBabBinding

class BabAdapter(private val babList: List<Bab>) : RecyclerView.Adapter<BabAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemBabBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bab: Bab) {
            binding.apply {
                // Tampilkan data bab di sini
                kursus.text = bab.namaBab
                avatar.setImageResource(R.drawable.ic_univ)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBabBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bab = babList[position]
        holder.bind(bab)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, BelajarMateriActivity::class.java)
            intent.putExtra("ISI_BAB", bab.isi)
            intent.putExtra("NAMA_BAB", bab.namaBab)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = babList.size
}