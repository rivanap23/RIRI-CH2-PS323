package com.riridev.ririapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riridev.ririapp.R
import com.riridev.ririapp.data.remote.response.GetDiscussionResponseItem
import com.riridev.ririapp.databinding.DiscussItemBinding

class DiscussAdapter(
    val likeClick: (GetDiscussionResponseItem) -> Boolean,
    val onClick: (GetDiscussionResponseItem) -> Unit
): ListAdapter<GetDiscussionResponseItem, DiscussAdapter.DiscussViewHolder>(DIFF_CALLBACK) {
    class DiscussViewHolder(private val binding: DiscussItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscussViewHolder {
        val binding = DiscussItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscussViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscussViewHolder, position: Int) {
        val discuss = getItem(position)
        holder.bind(discuss)
        val btnLike = holder.itemView.findViewById<ImageButton>(R.id.btn_like_item)
        btnLike.setOnClickListener {
            val result = likeClick(discuss)
            Log.d("TAG", "onBindViewHolder: adapter $result")
            if (result){
                btnLike.setImageResource(R.drawable.baseline_thumb_up_alt_24)
            } else {
                btnLike.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
            }
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
