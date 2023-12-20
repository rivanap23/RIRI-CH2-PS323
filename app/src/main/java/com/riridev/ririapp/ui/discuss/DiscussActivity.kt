package com.riridev.ririapp.ui.discuss

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.data.remote.response.GetDiscussionResponseItem
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.ActivityDiscussBinding
import com.riridev.ririapp.ui.DiscussViewModelFactory
import com.riridev.ririapp.ui.adapter.DiscussAdapter
import com.riridev.ririapp.ui.adddiscuss.AddDiscussActivity
import com.riridev.ririapp.ui.detaildiscuss.DetailDiscussActivity

class DiscussActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiscussBinding
    private val disscussViewModel: DisscussViewModel by viewModels {
        DiscussViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscussBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getData()
        setupAction()
    }

    override fun onResume() {
        super.onResume()
        disscussViewModel.getAllDiscussion()
    }

    private fun getData() {
        disscussViewModel.getDiscussion.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                }

                is Result.Success -> {
                    val response = result.data
                    setupRecyclerView(response)
                }

                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setupRecyclerView(response: List<GetDiscussionResponseItem>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvDiscuss.layoutManager = layoutManager
        val adapter = DiscussAdapter(
            likeClick = {
                disscussViewModel.addLikeDiscussion(it.postId).observe(this) { result ->
                    when (result) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            disscussViewModel.getAllDiscussion()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        ) {
            val intent = Intent(this, DetailDiscussActivity::class.java)
            intent.putExtra("post_id", it.postId)
            startActivity(intent)
        }
        adapter.submitList(response)
        binding.rvDiscuss.adapter = adapter
    }

    private fun setupAction() {
        binding.fabDiscuss.setOnClickListener {
            val intent = Intent(this, AddDiscussActivity::class.java)
            startActivity(intent)
        }
    }

}