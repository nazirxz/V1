package com.example.suksesdidikan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemBabBinding
import com.example.suksesdidikan.model.BabInfo

class BabAdapter(private val babInfoList: List<BabInfo>) :
    RecyclerView.Adapter<BabAdapter.BabInfoViewHolder>() {

    private var onItemClick: ((BabInfo) -> Unit)? = null

    fun setOnItemClickListener(listener: (BabInfo) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BabInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBabBinding.inflate(inflater, parent, false)
        return BabInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BabInfoViewHolder, position: Int) {
        val babInfo = babInfoList[position]
        holder.bind(babInfo)
    }

    override fun getItemCount(): Int = babInfoList.size

    inner class BabInfoViewHolder(private val binding: ItemBabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(babInfo: BabInfo) {
            binding.kursus.text = babInfo.bab

            // Handle click on item
            binding.root.setOnClickListener {
                onItemClick?.invoke(babInfo)
            }
        }
    }
}