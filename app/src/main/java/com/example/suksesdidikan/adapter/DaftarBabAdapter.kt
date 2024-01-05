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

    // Membuat ParentViewHolder berdasarkan tampilan item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMateribabBinding.inflate(inflater, parent, false) // Menggunakan DataBinding untuk tampilan item
        return ParentViewHolder(binding)
    }

    // Mengikat data dari BabInfo ke tampilan pada ViewHolder
    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val babInfo = filteredList[position]
        holder.bind(babInfo)
    }

    // Mendapatkan jumlah total item dalam daftar
    override fun getItemCount(): Int {
        return filteredList.size
    }

    inner class ParentViewHolder(private val binding: ItemMateribabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Menghubungkan data BabInfo ke tampilan item pada ViewHolder
        fun bind(babInfo: BabInfo) {
            binding.namaBab.text = babInfo.bab // Mengatur teks pada tampilan item dengan nama bab

            val childAdapter = SubBabAdapter(babInfo.isi) // Membuat adaptor untuk RecyclerView anak
            binding.childRV.apply {
                layoutManager = LinearLayoutManager(itemView.context) // Mengatur tata letak tampilan
                adapter = childAdapter // Mengatur adaptor untuk RecyclerView anak

                // Menangani klik item untuk childRV
                childAdapter.setOnItemClickListener { babDetail ->
                    itemClickListener?.invoke(babDetail, babInfo) // Memanggil fungsi saat item anak diklik
                }
            }
        }
    }

    // Fungsi untuk mengatur listener saat item diklik
    fun setOnItemClickListener(listener: (BabDetail, BabInfo) -> Unit) {
        itemClickListener = listener
    }
}
