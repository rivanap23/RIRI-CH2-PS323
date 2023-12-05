package com.riridev.ririapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.R
import com.riridev.ririapp.databinding.HomeLayoutBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation()
    }

    private fun bottomNavigation() {
        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.item_home -> {
                    true
                }
                R.id.item_chat -> {
                    true
                }
                R.id.item_notif -> {
                    true
                }
                R.id.item_profile -> {
                    true
                }
                else -> false
            }
        }
    }

}