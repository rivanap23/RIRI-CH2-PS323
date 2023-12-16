package com.riridev.ririapp.ui.adddiscuss

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.databinding.ActivityAddDiscussBinding
import com.riridev.ririapp.utils.getImageUri

class AddDiscussActivity : AppCompatActivity() {
    private var currentImageUri: Uri? = null
    private lateinit var binding: ActivityAddDiscussBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiscussBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupAction()
    }

    private fun setupAction() {
        binding.btnKamera.setOnClickListener {
            startCamera()
        }
        binding.btnGaleri.setOnClickListener {
            startGallery()
        }
        binding.btnKirimDiskusi.setOnClickListener {
            sendDiscussion()
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
            binding.ivDiskusi.setImageURI(it)
        }
    }

    private fun sendDiscussion() {
        val title = binding.etTitleDiscuss.text.toString()
        val description = binding.etIsiDiscuss.text.toString()

        Toast.makeText(this, "$title $description", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}