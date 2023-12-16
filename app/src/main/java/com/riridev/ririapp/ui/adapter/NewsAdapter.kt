package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riridev.ririapp.data.model.NewsModel
import com.riridev.ririapp.databinding.NewsItemBinding

class NewsAdapter(
    val onClick: (NewsModel) -> Unit
) : ListAdapter<NewsModel, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK){
    class NewsViewHolder(private val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: NewsModel) {
            binding.tvTitleNews.text = newsModel.title
            binding.tvDescNews.text = newsModel.description
            binding.ivNews.setImageDrawable(ContextCompat.getDrawable(binding.root.context, newsModel.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
        holder.itemView.setOnClickListener {
            onClick(news)
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<NewsModel>() {
                override fun areItemsTheSame(
                    oldItem: NewsModel,
                    newItem: NewsModel,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: NewsModel,
                    newItem: NewsModel,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}