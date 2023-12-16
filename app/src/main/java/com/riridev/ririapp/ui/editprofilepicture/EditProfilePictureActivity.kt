package com.riridev.ririapp.ui.editprofilepicture

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.riridev.ririapp.R
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.ActivityEditProfilePictureBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.profile.ProfileViewModel
import com.riridev.ririapp.utils.getImageUri
import com.riridev.ririapp.utils.uriToFile

class EditProfilePictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfilePictureBinding
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilePictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val url = intent.getStringExtra("profile_url").toString()

        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.baseline_account_circle_24)
            .into(binding.ivProfilePicture)


        binding.btnGaleri.setOnClickListener {
            startGallery()
        }

        binding.btnKamera.setOnClickListener {
            startCamera()
        }

        updatePicture()
    }

    private fun updatePicture(){
        binding.btnKirimDiskusi.setOnClickListener {
            currentImageUri?.let {
                val file = uriToFile(it, this)
                Log.d("TAG", "updatePicture: $it")
                profileViewModel.updateProfilePicture(file).observe(this){result ->
                    when(result){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            showToast(result.data.message)
                            Log.d("TAG", "updatePicture: ${result.data.url}")
                            finish()
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }
                    }

                }
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia(),
        ) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                showImage()
            } else {
                Log.d("Photo Picker", "No media selected")
            }
        }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera =
        registerForActivityResult(
            ActivityResultContracts.TakePicture(),
        ) { isSuccess ->
            if (isSuccess) {
                showImage()
            }
        }

    private fun showImage() {
        currentImageUri?.let {
            binding.ivProfilePicture.setImageURI(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingIndicator.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}