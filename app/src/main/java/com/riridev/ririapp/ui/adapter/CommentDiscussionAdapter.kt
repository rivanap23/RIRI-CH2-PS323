package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.remote.response.CommentsItem
import com.riridev.ririapp.databinding.CommentItemBinding

class CommentDiscussionAdapter : ListAdapter<CommentsItem, CommentDiscussionAdapter.CommentViewHolder>(DIFF_CALLBACK) {
    class CommentViewHolder(
        private val binding: CommentItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentsItem){
            Glide.with(binding.root)
                .load(comment.userProfileImage)
                .into(binding.ivProfileComment)

            binding.tvCommentDescription.text = comment.comment
            binding.yourname.text = comment.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<CommentsItem>() {
                override fun areItemsTheSame(
                    oldItem: CommentsItem,
                    newItem: CommentsItem,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: CommentsItem,
                    newItem: CommentsItem,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}