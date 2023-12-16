package com.riridev.ririapp.ui.editprofilepicture

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.riridev.ririapp.databinding.ActivityEditProfilePictureBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.profile.ProfileViewModel

class EditProfilePictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfilePictureBinding
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilePictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val url = intent.getStringExtra("profile_url").toString()


        binding.btnGaleri.setOnClickListener {

        }

        binding.btnKirimDiskusi.setOnClickListener {

        }
        Glide.with(this)
            .load(url)
            .into(binding.ivProfilePicture)
    }
}