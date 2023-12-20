package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.remote.response.GetDiscussionResponseItem
import com.riridev.ririapp.databinding.DiscussItemBinding

class DiscussAdapter(
    val likeClick: (GetDiscussionResponseItem) -> Unit,
    val onClick: (GetDiscussionResponseItem) -> Unit
) : ListAdapter<GetDiscussionResponseItem, DiscussAdapter.DiscussViewHolder>(DIFF_CALLBACK) {
    class DiscussViewHolder(private val binding: DiscussItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val buttonLike = binding.btnLikeItem
        fun bind(discuss: GetDiscussionResponseItem) {
            binding.title.text = discuss.title
            binding.yourname.text = discuss.username
            binding.descriptionDiscuss.text = discuss.content
            Glide.with(binding.root)
                .load(discuss.userProfileImage)
                .into(binding.imageView)
            Glide.with(binding.root)
                .load(discuss.imageUrl)
                .into(binding.ivDisccuss)
            binding.tvLikesItem.text = "${discuss.likes} likes"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscussViewHolder {
        val binding = DiscussItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscussViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscussViewHolder, position: Int) {
        val discuss = getItem(position)
        holder.bind(discuss)
        holder.buttonLike.setOnClickListener {
            likeClick(discuss)
        }
        holder.itemView.setOnClickListener {
            onClick(discuss)
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<GetDiscussionResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: GetDiscussionResponseItem,
                    newItem: GetDiscussionResponseItem,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: GetDiscussionResponseItem,
                    newItem: GetDiscussionResponseItem,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
