package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.local.room.entity.DiscussionEntity
import com.riridev.ririapp.databinding.DiscussItemBinding

class DiscussionAdapter(
    val onClick: (DiscussionEntity) -> Unit
): PagingDataAdapter<DiscussionEntity, DiscussionAdapter.DiscussionViewHolder>(DIFF_CALLBACK) {
    class DiscussionViewHolder(private val binding: DiscussItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(discussionEntity: DiscussionEntity){
            binding.title.text = discussionEntity.title
            binding.yourname.text = discussionEntity.username
            binding.descriptionDiscuss.text = discussionEntity.content
            Glide.with(binding.root)
                .load(discussionEntity.userProfileImage)
                .into(binding.imageView)
            Glide.with(binding.root)
                .load(discussionEntity.imageUrl)
                .into(binding.ivDisccuss)
        }
    }

    override fun onBindViewHolder(holder: DiscussionViewHolder, position: Int) {
       val discuss = getItem(position)
        if (discuss != null){
            holder.bind(discuss)
            holder.itemView.setOnClickListener {
                onClick(discuss)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscussionViewHolder {
       val binding = DiscussItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscussionViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<DiscussionEntity>() {
                override fun areItemsTheSame(
                    oldItem: DiscussionEntity,
                    newItem: DiscussionEntity,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: DiscussionEntity,
                    newItem: DiscussionEntity,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }


}