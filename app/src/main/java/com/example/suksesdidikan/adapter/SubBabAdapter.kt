package com.example.suksesdidikan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemSubbabBinding
import com.example.suksesdidikan.model.BabDetail

class SubBabAdapter(private val list: List<BabDetail>) :
    RecyclerView.Adapter<SubBabAdapter.ChildViewHolder>() {
    private var onItemClickListener: ((BabDetail) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubbabBinding.inflate(inflater, parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val babDetail = list[position]
        holder.bind(babDetail)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ChildViewHolder(private val binding: ItemSubbabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(babDetail: BabDetail) {
            binding.subbab.text = babDetail.judul

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(babDetail)
            }
        }
    }
    // Function to set item click listener
    fun setOnItemClickListener(listener: (BabDetail) -> Unit) {
        onItemClickListener = listener
    }
}

