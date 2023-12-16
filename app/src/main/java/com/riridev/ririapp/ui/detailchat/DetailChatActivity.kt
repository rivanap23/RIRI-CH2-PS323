package com.riridev.ririapp.ui.detailchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.databinding.ActivityDetailChatBinding

class DetailChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        backAction()
    }

    private fun backAction() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}