package com.riridev.ririapp.ui.adddiscuss

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.data.model.DiscussionModel
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.ActivityAddDiscussBinding
import com.riridev.ririapp.ui.DiscussViewModelFactory
import com.riridev.ririapp.utils.getImageUri
import com.riridev.ririapp.utils.uriToFile

class AddDiscussActivity : AppCompatActivity() {
    private var currentImageUri: Uri? = null
    private lateinit var binding: ActivityAddDiscussBinding
    private val addDiscussViewModel: AddDiscussViewModel by viewModels {
        DiscussViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiscussBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        addDiscussViewModel.addDiscuss.observe(this){ result ->
            when(result){
                is Result.Loading -> {
                    showLoading(true)
                    binding.btnKirimDiskusi.isEnabled = false
                }
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
        currentImageUri?.let {
            val title = binding.etTitleDiscuss.text.toString()
            val description = binding.etIsiDiscuss.text.toString()
            val imageFile = uriToFile(it, this)
            val discussionModel = DiscussionModel(title, description, imageFile)
            addDiscussViewModel.createDiscussion(discussionModel)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingIndicator.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}