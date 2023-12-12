package com.riridev.ririapp.ui.history

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.riridev.ririapp.R
import com.riridev.ririapp.databinding.ActivityHistoryBinding
import com.riridev.ririapp.ui.adapter.SectionPagerAdapter

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupSectionPager()
    }

    private fun setupSectionPager() {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_verifikasi,
            R.string.tab_diproses,
            R.string.tab_selesai
        )
    }
}