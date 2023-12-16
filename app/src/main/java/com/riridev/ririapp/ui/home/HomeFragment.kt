package com.riridev.ririapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.riridev.ririapp.data.dummy.ImageDummy.Companion.imageList
import com.riridev.ririapp.databinding.FragmentHomeBinding
import com.riridev.ririapp.ui.adapter.HomeCarouselAdapter
import com.riridev.ririapp.ui.discuss.DiscussActivity
import com.riridev.ririapp.ui.emergency.EmergencyActivity
import com.riridev.ririapp.ui.history.HistoryActivity
import com.riridev.ririapp.ui.news.NewsActivity
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
        setupActionBottomNav()
        setupCarousel()
    }

    private fun setupCarousel() {
        binding?.homeLayout?.carouselRecyclerView?.layoutManager = CarouselLayoutManager()
        val carouselAdapter = HomeCarouselAdapter()
        binding?.homeLayout?.carouselRecyclerView?.adapter = carouselAdapter
        carouselAdapter.submitList(imageList)
        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(binding?.homeLayout?.carouselRecyclerView)
    }

    private fun setupActionBottomNav(){
        binding?.homeLayout?.btnReport?.setOnClickListener {
            val intent = Intent(requireContext(), ReportActivity::class.java)
            startActivity(intent)
        }

        binding?.homeLayout?.btnEmergency?.setOnClickListener {
            val intent = Intent(requireContext(), EmergencyActivity::class.java)
            startActivity(intent)
        }

        binding?.homeLayout?.btnHistory?.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        binding?.homeLayout?.btnForum?.setOnClickListener {
            val intent = Intent(requireContext(), DiscussActivity::class.java)
            startActivity(intent)
        }

        binding?.homeLayout?.btnNews?.setOnClickListener {
            val intent = Intent(requireContext(), NewsActivity::class.java)
            startActivity(intent)
        }
    }
}