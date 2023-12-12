package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.model.Discuss
import com.riridev.ririapp.databinding.DiscussItemBinding

class DiscussAdapter(
    val onClick: (Discuss) -> Unit
): ListAdapter<Discuss, DiscussAdapter.DiscussViewHolder>(DIFF_CALLBACK) {
    class DiscussViewHolder(private val binding: DiscussItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(discuss: Discuss) {
            binding.title.text = discuss.title
            binding.yourname.text = discuss.nama
            binding.descriptionDiscuss.text = discuss.isi
            Glide.with(binding.root)
                .load(discuss.imageUrl)
                .into(binding.ivDisccuss)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscussViewHolder {
        val binding = DiscussItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscussViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscussViewHolder, position: Int) {
        val discuss = getItem(position)
        holder.bind(discuss)
        holder.itemView.setOnClickListener {
            onClick(discuss)
        }
    }
    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Discuss>() {
                override fun areItemsTheSame(
                    oldItem: Discuss,
                    newItem: Discuss,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: Discuss,
                    newItem: Discuss,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
