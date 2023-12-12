package com.riridev.ririapp.ui.discuss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.databinding.ActivityAddDiscussBinding

class AddDiscussActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDiscussBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiscussBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}