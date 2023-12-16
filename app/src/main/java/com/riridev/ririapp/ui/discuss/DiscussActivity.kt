package com.riridev.ririapp.ui.discuss

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.data.dummy.DisscussDummy
import com.riridev.ririapp.databinding.ActivityDiscussBinding
import com.riridev.ririapp.ui.adapter.DiscussAdapter
import com.riridev.ririapp.ui.adddiscuss.AddDiscussActivity
import com.riridev.ririapp.ui.detaildiscuss.DetailDiscussActivity

class DiscussActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiscussBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscussBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupRecyclerView()
        setupAction()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        binding.rvDiscuss.layoutManager = layoutManager

        val adapter = DiscussAdapter {
            val intent = Intent(this, DetailDiscussActivity::class.java)
            intent.putExtra("discuss", it)
            startActivity(intent)
        }
        adapter.submitList(DisscussDummy.disscuss)
        binding.rvDiscuss.adapter = adapter
    }

    private fun setupAction() {
        binding.fabDiscuss.setOnClickListener {
            val intent = Intent(this, AddDiscussActivity::class.java)
            startActivity(intent)
        }
    }
}