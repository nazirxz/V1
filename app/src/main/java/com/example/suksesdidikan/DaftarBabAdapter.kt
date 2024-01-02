package com.example.suksesdidikan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemMateribabBinding

class DaftarBabAdapter(private val bukuList: List<Buku>) :
    RecyclerView.Adapter<DaftarBabAdapter.BelajarMateriViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BelajarMateriViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMateribabBinding.inflate(inflater, parent, false)
        return BelajarMateriViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BelajarMateriViewHolder, position: Int) {
        val buku = bukuList[position]
        holder.bind(buku)
    }

    override fun getItemCount(): Int {
        return bukuList.size
    }

    inner class BelajarMateriViewHolder(private val binding: ItemMateribabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val subRecyclerView: RecyclerView = binding.rvJudulSub
        private lateinit var subAdapter: SubBabAdapter

        fun bind(buku: Buku) {
            // Extract keys (namaBab values) from the isiBab map
            val namaBabList = buku.isiBab.keys.toList()

            // Join the namaBabList to display multiple keys (namaBab) in the TextView
            val joinedNamaBab = namaBabList.joinToString(", ")

            // Set the joined namaBab text to the TextView
            binding.namaBab.text = joinedNamaBab

            val babList = buku.isiBab.values.toList()

            subAdapter = SubBabAdapter(babList)
            subRecyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = subAdapter
            }

            // Handle click on sub-item
            subAdapter.setOnItemClickListener { clickedBab ->
                // Implement your logic here when a sub-item is clicked
                // For example, open another activity with details or perform some action
            }
        }
    }
}
