package com.riridev.ririapp.ui.detaildiscuss

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.riridev.ririapp.R
import com.riridev.ririapp.data.remote.response.CommentsItem
import com.riridev.ririapp.data.remote.response.GetDiscussionDetailResponse
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.ActivityDetailDiscussBinding
import com.riridev.ririapp.ui.DiscussViewModelFactory
import com.riridev.ririapp.ui.adapter.CommentDiscussionAdapter
import com.riridev.ririapp.utils.DateConverter

class DetailDiscussActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDiscussBinding
    private val detailDiscussViewModel: DetailDiscussViewModel by viewModels {
        DiscussViewModelFactory.getInstance(this)
    }
    private var isCommentShowed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val postId = intent.getStringExtra("post_id").toString()

        getDetailData(postId)
        setupAction(postId)
    }

    private fun setupAction(postId: String) {
        binding.sendButton.setOnClickListener {
            //send comment
            val comment = binding.messageEditText.text.toString()
            detailDiscussViewModel.createCommentUser(postId, comment).observe(this) { result ->
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        binding.messageEditText.setText("")
                        getDetailData(postId)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        binding.messageEditText.setText("")
                        showToast(result.error)
                    }
                }
            }
        }

        binding.btnLike.setOnClickListener {
            //set like
            detailDiscussViewModel.addLikeDiscussion(postId).observe(this){result ->
                when(result){
                    is Result.Loading -> {
                    }
                    is Result.Success -> {
                        getDetailData(postId)
                    }
                    is Result.Error -> {
                    }
                }
            }
        }

        binding.btnComment.setOnClickListener {
            if (!isCommentShowed){
                binding.linearLayout.visibility = View.VISIBLE
                isCommentShowed = true
            } else {
                binding.linearLayout.visibility = View.GONE
                isCommentShowed = false
            }
        }
    }

    private fun getDetailData(postId: String) {
        detailDiscussViewModel.getDetailDiscussion(postId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(false)
                }
                is Result.Success -> {
                    val dataDetail = result.data
                    bindData(dataDetail)
                    if (dataDetail.comments.isEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.linearLayout.visibility = View.GONE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.linearLayout.visibility = View.VISIBLE
                    }
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        }
    }

    private fun bindData(dataDetail: GetDiscussionDetailResponse) {
        binding.titleDetail.text = dataDetail.title
        binding.yourname.text = dataDetail.username
        binding.descriptionDiscuss.text = dataDetail.content
        binding.tvLikeCount.text = getString(R.string.likes, dataDetail.likes.toString())

        binding.dateDiscuss.text =
            DateConverter.getDateString(dataDetail.timestamp.seconds.toLong(), "dd/MM/yyyy")
        Glide.with(binding.root)
            .load(dataDetail.imageUrl)
            .into(binding.ivDisccuss)
        Glide.with(binding.root)
            .load(dataDetail.userProfileImage)
            .into(binding.imageView)
        setupRecyclerView(dataDetail.comments)
    }

    private fun setupRecyclerView(comments: List<CommentsItem?>?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CommentDiscussionAdapter()
        adapter.submitList(comments)
        binding.recyclerView.adapter = adapter
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingIndicator.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}