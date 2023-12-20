package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riridev.ririapp.R
import com.riridev.ririapp.data.remote.response.ProcessedReportsItem
import com.riridev.ririapp.databinding.LayoutHistoryItemBinding
import com.riridev.ririapp.utils.DateConverter

class HistoryAdapter(
    val onClick: (ProcessedReportsItem) -> Unit,
) : ListAdapter<ProcessedReportsItem, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {
    class HistoryViewHolder(private val binding: LayoutHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(report: ProcessedReportsItem) {
            when (report.status) {
                "verifikasi" -> {
                    binding.imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.imageView.context,
                            R.drawable.indicator_verification_history
                        )
                    )
                }
                "selesai" -> {
                    binding.imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.imageView.context,
                            R.drawable.indicatior_done_history
                        )
                    )
                }
                "ditolak" -> {
                    binding.imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.imageView.context,
                            R.drawable.indicator_rejected_history
                        )
                    )
                }
                "diproses" -> {
                    binding.imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.imageView.context,
                            R.drawable.indicator_process_history
                        )
                    )
                }
                else -> {
                    binding.imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.imageView.context,
                            R.drawable.indicator_verification_history
                        )
                    )
                }
            }

            binding.title.text = report.judulLaporan
            binding.location.text = report.lokasi
            binding.date.text =
                DateConverter.getDateString(report.createdAt.seconds.toLong(), "dd/MM/yyyy")
        }

        fun afterCliCk(){
            binding.title.maxLines = 3
            binding.location.maxLines = 5
        }

        fun beforeClick(){
            binding.title.maxLines = 1
            binding.location.maxLines = 1
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding =
            LayoutHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val report = getItem(position)
        var isClicked = true
        holder.bind(report)
        holder.itemView.setOnClickListener {
            if (isClicked){
                holder.afterCliCk()
            } else {
                holder.beforeClick()
            }
            isClicked = !isClicked
            onClick(report)
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ProcessedReportsItem>() {
                override fun areItemsTheSame(
                    oldItem: ProcessedReportsItem,
                    newItem: ProcessedReportsItem,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ProcessedReportsItem,
                    newItem: ProcessedReportsItem,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}