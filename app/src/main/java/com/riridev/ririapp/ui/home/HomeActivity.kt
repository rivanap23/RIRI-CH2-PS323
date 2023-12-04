package com.riridev.ririapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.databinding.HomeLayoutBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}