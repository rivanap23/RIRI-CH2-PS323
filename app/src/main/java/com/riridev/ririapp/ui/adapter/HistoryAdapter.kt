package com.riridev.ririapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riridev.ririapp.R
import com.riridev.ririapp.data.model.Report
import com.riridev.ririapp.databinding.LayoutHistoryItemBinding

class HistoryAdapter() : ListAdapter<Report, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {
    class HistoryViewHolder(private val binding: LayoutHistoryItemBinding):
        RecyclerView.ViewHolder(binding.root)  {
        fun bind(report: Report) {
            if (report.isDone){
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(binding.imageView.context, R.drawable.indicatior_done_history))
            } else {
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(binding.imageView.context, R.drawable.indicator_process_history))
            }
            binding.title.text = report.title
            binding.location.text = report.location
            binding.date.text = report.date
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding = LayoutHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val report = getItem(position)
        holder.bind(report)
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Report>() {
                override fun areItemsTheSame(
                    oldItem: Report,
                    newItem: Report,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: Report,
                    newItem: Report,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}