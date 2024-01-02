package com.example.suksesdidikan

import Buku
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.DummyData.dummyList
import com.example.suksesdidikan.databinding.ItemMateribabBinding

class DaftarBabAdapter(private var filteredList: List<Buku>) :
    RecyclerView.Adapter<DaftarBabAdapter.BelajarMateriViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BelajarMateriViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMateribabBinding.inflate(inflater, parent, false)
        return BelajarMateriViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BelajarMateriViewHolder, position: Int) {
        val buku = filteredList[position]
        holder.bind(buku)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun filterByMatapelajaran(matapelajaran: String) {
        filteredList = dummyList.filter { it.matapelajaran.equals(matapelajaran, ignoreCase = true) }
        notifyDataSetChanged()
    }

    inner class BelajarMateriViewHolder(private val binding: ItemMateribabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val subRecyclerView: RecyclerView = binding.rvJudulSub
        private lateinit var subAdapter: SubBabAdapter

        fun bind(buku: Buku) {
            binding.namaBab.text = buku.matapelajaran

            val babList = buku.bab.flatMap { it.isi }
            subAdapter = SubBabAdapter(babList)

            subRecyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = subAdapter
            }

            // Handle click on sub-item
            subAdapter.setOnItemClickListener { clickedBab ->
                // Handle your logic here for sub-item click
            }
        }
    }
}