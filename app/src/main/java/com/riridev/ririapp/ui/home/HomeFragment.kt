package com.riridev.ririapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.riridev.ririapp.databinding.FragmentHomeBinding
import com.riridev.ririapp.ui.emergency.EmergencyActivity
import com.riridev.ririapp.ui.report.ReportActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionMenu()
    }

    private fun setupActionMenu(){
        binding?.homeLayout?.btnReport?.setOnClickListener {
            val intent = Intent(requireContext(), ReportActivity::class.java)
            startActivity(intent)
        }

        binding?.homeLayout?.btnEmergency?.setOnClickListener {
            val intent = Intent(requireContext(), EmergencyActivity::class.java)
            startActivity(intent)
        }
    }
}