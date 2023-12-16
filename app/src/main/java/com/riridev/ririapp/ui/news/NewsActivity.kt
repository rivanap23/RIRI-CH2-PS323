package com.riridev.ririapp.ui.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.data.dummy.NewsDummy
import com.riridev.ririapp.databinding.ActivityNewsBinding
import com.riridev.ririapp.ui.adapter.NewsAdapter

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRecyclerview()
    }

    private fun setupRecyclerview() {

        binding.rvNews.layoutManager = LinearLayoutManager(this)
        val adapter = NewsAdapter{
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.DETAIL_INTENT, it)
            startActivity(intent)
        }
        adapter.submitList(NewsDummy.listNews)
        binding.rvNews.adapter = adapter
    }
}