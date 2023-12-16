package com.riridev.ririapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.RegisterLayoutBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.login.LoginActivity
import com.riridev.ririapp.utils.DateConverter

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegisterLayoutBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        registerViewModel.register.observe(this){ result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    //nanti ganti pake snackbar
                    showToast(result.data.message)
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        }
    }

    private fun setupAction() {
        //date picker action
        binding.layoutRegis.textInputLayout5.setEndIconOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(supportFragmentManager, "date")
            datePicker.addOnPositiveButtonClickListener {
                binding.layoutRegis.etBirthDate.setText(DateConverter.convertMillisToString(it))
            }
        }

        //register action
        binding.layoutRegis.btnRegister.setOnClickListener {
            register()
        }

        //to login action
        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun register() {
        val email = binding.layoutRegis.etEmailRegister.text.toString()
        val username = binding.layoutRegis.etUsernameRegister.text.toString()
        val placeOfBirth = binding.layoutRegis.etBirthPlace.text.toString()
        val dateOfBirth = binding.layoutRegis.etBirthDate.text.toString()
        val pass = binding.layoutRegis.etPassword.text.toString()
        val rePass = binding.layoutRegis.etRepassword.text.toString()

        registerViewModel.register(
            email,
            username,
            placeOfBirth,
            dateOfBirth,
            pass,
            rePass
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.layoutRegis.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}