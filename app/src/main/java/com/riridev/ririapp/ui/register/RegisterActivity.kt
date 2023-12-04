package com.riridev.ririapp.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.databinding.RegisterLayoutBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegisterLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}