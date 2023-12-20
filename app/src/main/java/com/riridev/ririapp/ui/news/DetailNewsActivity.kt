package com.riridev.ririapp.ui.news

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.riridev.ririapp.R
import com.riridev.ririapp.data.model.NewsModel
import com.riridev.ririapp.databinding.ActivityDetailNewsBinding


class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topAppBar.title = " "
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        @Suppress("DEPRECATION")
        val newsModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DETAIL_INTENT, NewsModel::class.java)
        } else {
            intent.getParcelableExtra(DETAIL_INTENT)
        }

        if (newsModel != null) {
            binding.ivNewsDetail.setImageDrawable(ContextCompat.getDrawable(this, newsModel.image))
            binding.tvTitleDetailNews.text = newsModel.title
            binding.tvDateDetailNews.text = newsModel.dateTime
            binding.tvAuthorDetailNews.text =
                getString(R.string.author_news, newsModel.author, newsModel.editor)
            binding.tvDescriptionNews.text = newsModel.description
            binding.tvSourceNews.text = newsModel.source
        }
    }

    companion object {
        const val DETAIL_INTENT = "detail_key"
    }
}