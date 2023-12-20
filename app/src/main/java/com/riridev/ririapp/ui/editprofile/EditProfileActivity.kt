package com.riridev.ririapp.ui.editprofile

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.ActivityEditProfileBinding
import com.riridev.ririapp.ui.ViewModelFactory

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val username = intent.getStringExtra(USERNAME).toString()
        val email = intent.getStringExtra(EMAIL).toString()
        val placeOfBirth = intent.getStringExtra(PLACE).toString()
        val dateOfBirth = intent.getStringExtra(DATE).toString()

        binding.etUsernameEdit.setText(username)
        binding.etBirthDateEdit.setText(dateOfBirth)
        binding.etEmailEdit.setText(email)
        binding.etBirthPlace.setText(placeOfBirth)
        binding.etUsernameEdit.isEnabled = false
        binding.etBirthPlace.isEnabled = false
        binding.etBirthDateEdit.isEnabled = false

        binding.btnRegister.setOnClickListener {
            val newEmail = binding.etEmailEdit.text.toString()
            val newUsername = binding.etUsernameEdit.text.toString()
            if (email == newEmail) {
                editProfileViewModel.editUsername(newUsername).observe(this) { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            finish()
                        }

                        is Result.Error -> {
                            showLoading(false)
                            finish()
                        }
                    }
                }
            } else if (username == newUsername) {
                editProfileViewModel.editEmail(newEmail).observe(this) { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            finish()
                        }

                        is Result.Error -> {
                            showLoading(false)
                            finish()
                        }
                    }
                }
            } else {
                editProfileViewModel.updateProfileInfo(newEmail, newUsername)
                    .observe(this) { result ->
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }

                            is Result.Success -> {
                                showLoading(false)
                                finish()
                            }

                            is Result.Error -> {
                                showLoading(false)
                                finish()
                            }
                        }
                    }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingIndicator.visibility = View.VISIBLE
        } else {
            binding.loadingIndicator.visibility = View.GONE
        }
    }

    companion object {
        const val USERNAME = "username_key"
        const val EMAIL = "email_key"
        const val PLACE = "place_key"
        const val DATE = "date_key"
    }
}