package com.riridev.ririapp.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riridev.ririapp.data.model.NotificationModel
import com.riridev.ririapp.databinding.NotificationItemBinding

class NotifAdapter(
    val onClick: (NotificationModel) -> Unit
) : ListAdapter<NotificationModel, NotifAdapter.NotificationViewHolder>(DIFF_CALLBACK) {
    class NotificationViewHolder(private val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(notif: NotificationModel){
                binding.title.text = notif.title
                binding.descNotif.text = notif.message
                binding.date.text = notif.date
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notif = getItem(position)
        holder.bind(notif)
        holder.itemView.setOnClickListener {
            onClick(notif)
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<NotificationModel>() {
                override fun areItemsTheSame(
                    oldItem: NotificationModel,
                    newItem: NotificationModel,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: NotificationModel,
                    newItem: NotificationModel,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}