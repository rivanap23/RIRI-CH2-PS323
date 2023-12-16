package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riridev.ririapp.data.model.Chat
import com.riridev.ririapp.databinding.ChatItemBinding
class ChatAdapter(
    val onClick: (Chat) -> Unit,
): ListAdapter<Chat, ChatAdapter.ChatViewHolder>(DIFF_CALLBACK)  {
    class ChatViewHolder (private val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.tvUsername.text = chat.username
            binding.message.text = chat.message
            binding.date.text = chat.date
//            Glide.with(binding.root)
//                .load(chat.imageUrl)
//                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = getItem(position)
        holder.bind(chat)
        holder.itemView.setOnClickListener {
            onClick(chat)
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Chat>() {
                override fun areItemsTheSame(
                    oldItem: Chat,
                    newItem: Chat,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: Chat,
                    newItem: Chat,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}