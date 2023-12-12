package com.riridev.ririapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.data.dummy.ReportDummy
import com.riridev.ririapp.databinding.FragmentDoneBinding
import com.riridev.ririapp.ui.adapter.HistoryAdapter

class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.rvHistory?.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = HistoryAdapter()
        adapter.submitList(ReportDummy.listReport.filter { report -> report.isDone })
        binding?.rvHistory?.adapter = adapter
    }
}