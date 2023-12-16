package com.riridev.ririapp.ui.detaildiscuss

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.model.Discuss
import com.riridev.ririapp.databinding.ActivityDetailDiscussBinding

class DetailDiscussActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDiscussBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val discuss = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("discuss", Discuss::class.java)
        }  else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Discuss>("discuss")
        }

        if (discuss != null){
            binding.titleDetail.text = discuss.title
            binding.yourname.text = discuss.nama
            binding.descriptionDiscuss.text = discuss.isi
            Glide.with(binding.root)
                .load(discuss.imageUrl)
                .into(binding.ivDisccuss)
        }
    }
}