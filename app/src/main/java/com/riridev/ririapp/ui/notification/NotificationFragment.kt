package com.riridev.ririapp.ui.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.R
import com.riridev.ririapp.data.dummy.NotifDummy
import com.riridev.ririapp.databinding.FragmentNotificationBinding
import com.riridev.ririapp.ui.adapter.NotifAdapter

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        binding?.notifLayout?.rvNotif?.layoutManager = LinearLayoutManager(requireContext())
        val adapter = NotifAdapter{
            Toast.makeText(requireContext(), getString(R.string.fitur_belum_tersedia), Toast.LENGTH_SHORT).show()
        }
        Log.d("TAG", "setupRecyclerView: $adapter")
        adapter.submitList(NotifDummy.listNotif)
        binding?.notifLayout?.rvNotif?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}