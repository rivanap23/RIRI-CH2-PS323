package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.model.ImageCarouselItem
import com.riridev.ririapp.databinding.HomeCarouselBinding

class HomeCarouselAdapter(): ListAdapter<ImageCarouselItem, HomeCarouselAdapter.HomeCarouselViewHolder>(DIFF_CALLBACK)  {
    class HomeCarouselViewHolder(private val binding: HomeCarouselBinding):
        RecyclerView.ViewHolder(binding.root)  {
        fun bind(image: ImageCarouselItem) {
            Glide.with(itemView)
                .load(image.url)
                .into(binding.carouselImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCarouselViewHolder {
        val binding = HomeCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCarouselViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ImageCarouselItem>() {
                override fun areItemsTheSame(
                    oldItem: ImageCarouselItem,
                    newItem: ImageCarouselItem,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ImageCarouselItem,
                    newItem: ImageCarouselItem,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}