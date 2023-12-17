package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riridev.ririapp.data.model.CarouselItemModel
import com.riridev.ririapp.databinding.CarouselItemBinding

class HomeCarouselAdapter : ListAdapter<CarouselItemModel, HomeCarouselAdapter.HomeCarouselViewHolder>(DIFF_CALLBACK)  {
    class HomeCarouselViewHolder(private val binding: CarouselItemBinding):
        RecyclerView.ViewHolder(binding.root)  {
        fun bind(item: CarouselItemModel) {
            binding.tvTitleCarousel.text = item.title
            binding.tvCarouselDesc.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCarouselViewHolder {
        val binding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCarouselViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<CarouselItemModel>() {
                override fun areItemsTheSame(
                    oldItem: CarouselItemModel,
                    newItem: CarouselItemModel,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: CarouselItemModel,
                    newItem: CarouselItemModel,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}