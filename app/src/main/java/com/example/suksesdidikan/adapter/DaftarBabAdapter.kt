package com.example.suksesdidikan.adapter

import com.example.suksesdidikan.model.Buku
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemMateribabBinding
import com.example.suksesdidikan.model.BabDetail
import com.example.suksesdidikan.model.BabInfo

class DaftarBabAdapter(
    private var babInfoList: List<BabInfo>,
    private var bukuList: List<Buku>
) : RecyclerView.Adapter<DaftarBabAdapter.ParentViewHolder>() {

    private var filteredList: List<BabInfo> = babInfoList
    private var itemClickListener: ((BabDetail, BabInfo) -> Unit)? = null
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
                    itemClickListener?.invoke(babDetail, babInfo)
                }
            }
        }
    }
    fun setOnItemClickListener(listener: (BabDetail, BabInfo) -> Unit) {
        itemClickListener = listener
    }
}