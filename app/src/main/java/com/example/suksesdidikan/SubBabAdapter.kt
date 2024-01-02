package com.example.suksesdidikan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suksesdidikan.databinding.ItemSubbabBinding

class SubBabAdapter(private val subBabList: List<Bab>) :
    RecyclerView.Adapter<SubBabAdapter.SubBabViewHolder>() {

    private var onItemClick: ((Bab) -> Unit)? = null

    fun setOnItemClickListener(listener: (Bab) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubBabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubbabBinding.inflate(inflater, parent, false)
        return SubBabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubBabViewHolder, position: Int) {
        val subBab = subBabList[position]
        holder.bind(subBab)
    }

    override fun getItemCount(): Int {
        return subBabList.size
    }

    inner class SubBabViewHolder(private val binding: ItemSubbabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subBab: Bab) {
            binding.subbab.text = subBab.namaBab

            // Handle click on sub-item
            binding.root.setOnClickListener {
                onItemClick?.invoke(subBab)
            }
        }
    }
}