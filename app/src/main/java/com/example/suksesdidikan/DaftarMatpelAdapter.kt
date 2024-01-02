package com.example.suksesdidikan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemMateriBinding

class DaftarMatpelAdapter(private val matpelList: List<Buku>) :
    RecyclerView.Adapter<DaftarMatpelAdapter.MatpelViewHolder>() {

    // Define a listener interface
    interface OnItemClickListener {
        fun onItemClick(buku: Buku)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatpelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMateriBinding.inflate(inflater, parent, false)
        return MatpelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatpelViewHolder, position: Int) {
        val matpel = matpelList[position]
        holder.bind(matpel)

        // Handle item click
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(matpel)
        }
    }

    override fun getItemCount(): Int {
        return matpelList.size
    }

    class MatpelViewHolder(private val binding: ItemMateriBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(matpel: Buku) {
            binding.materi.text = matpel.matapelajaran
            binding.avatar.setImageResource(matpel.avatar)
            binding.description.text = matpel.deskripsi
        }
    }
}
