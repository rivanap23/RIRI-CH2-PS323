package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.model.Instansi
import com.riridev.ririapp.databinding.EmergencyItemBinding

class EmergencyAdapter(
    val onClick: (Instansi) -> Unit
) : ListAdapter<Instansi, EmergencyAdapter.EmergencyViewHolder>(DIFF_CALLBACK) {
    class EmergencyViewHolder(private val binding: EmergencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(instansi: Instansi) {
            binding.nameInstansi.text = instansi.name
            Glide.with(binding.root)
                .load(instansi.logoUrl)
                .into(binding.logoInstansi)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmergencyViewHolder {
        val binding = EmergencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmergencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        val instansi = getItem(position)
        holder.bind(instansi)
        holder.itemView.setOnClickListener {
            onClick(instansi)
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