package com.riridev.ririapp.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.model.Instansi
import com.riridev.ririapp.databinding.ItemAgensiBinding


class ListAgensiAdapter(
    val onClick: (Instansi) -> Unit
): ListAdapter<Instansi, ListAgensiAdapter.ListAgensiViewHolder>(DIFF_CALLBACK) {
    class ListAgensiViewHolder(private val binding: ItemAgensiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(agensi: Instansi) {
            binding.nameInstansi.text = agensi.name
            Glide.with(binding.root)
                .load(agensi.logoUrl)
                .into(binding.logoInstansi)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAgensiViewHolder {
        val binding = ItemAgensiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAgensiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAgensiViewHolder, position: Int) {
        val agensi = getItem(position)
        holder.bind(agensi)
        holder.itemView.setOnClickListener {
            onClick(agensi)
        }
    }


    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Instansi>() {
                override fun areItemsTheSame(
                    oldItem: Instansi,
                    newItem: Instansi,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: Instansi,
                    newItem: Instansi,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
