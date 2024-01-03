package com.example.suksesdidikan

import Buku
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.DummyData.dummyList
import com.example.suksesdidikan.databinding.ItemMateribabBinding

class DaftarBabAdapter(
    private var babInfoList: List<BabInfo>,
    private var bukuList: List<Buku>
) : RecyclerView.Adapter<DaftarBabAdapter.ParentViewHolder>() {

    private var filteredList: List<BabInfo> = babInfoList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMateribabBinding.inflate(inflater, parent, false)
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val babInfo = filteredList[position]
        holder.bind(babInfo)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    inner class ParentViewHolder(private val binding: ItemMateribabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(babInfo: BabInfo) {
            binding.namaBab.text = babInfo.bab

            val childAdapter = SubBabAdapter(babInfo.isi)
            binding.childRV.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = childAdapter
                // Handle item click for childRV
                childAdapter.setOnItemClickListener { babDetail ->
                    val intent = Intent(itemView.context, BelajarMateriActivity::class.java)
                    intent.putExtra("ISI_BAB", babDetail.isi)
                    intent.putExtra("NAMA_BAB", babDetail.judul)
                    intent.putExtra("BAB",babInfo.bab)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}