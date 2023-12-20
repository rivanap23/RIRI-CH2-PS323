package com.riridev.ririapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.R
import com.riridev.ririapp.data.dummy.ChatDummy
import com.riridev.ririapp.databinding.FragmentChatBinding
import com.riridev.ririapp.ui.adapter.ChatAdapter

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        binding?.chatLayout?.rvChat?.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ChatAdapter {
            Toast.makeText(requireContext(),
                getString(R.string.fitur_belum_tersedia), Toast.LENGTH_SHORT).show()
        }
        binding?.chatLayout?.rvChat?.adapter = adapter
        adapter.submitList(ChatDummy.listChat)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}