package com.riridev.ririapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.riridev.ririapp.R
import com.riridev.ririapp.data.local.pref.UserModel
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.LoginLayoutBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.custom.CustomButton
import com.riridev.ririapp.ui.main.MainActivity
import com.riridev.ririapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginLayoutBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInput()
        setupAction()

        loginViewModel.login.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val user = UserModel(
                        result.data.userId,
                        result.data.username,
                        result.data.accessToken,
                        true
                    )
                    loginViewModel.saveSession(user)
                    //ganti pake snackbar
                    val intent = Intent(this, MainActivity::class.java)
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
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.activityLogin.btnLogin.setOnClickListener {
            val email = binding.activityLogin.etEmailUsername.text.toString()
            val pass = binding.activityLogin.etPassword.text.toString()
            loginViewModel.loginUser(email, pass)
        }
        binding.activityLogin.tvForgotPassword.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_forget_password, null)
        val button = view.findViewById<CustomButton>(R.id.btn_send_forgot)
        button.setOnClickListener {
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
        }
        MaterialAlertDialogBuilder(this)
            .setView(view)
            .show()
    }


    private fun checkInput() {
        binding.activityLogin.etEmailUsername.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //do nothing
                }

                override fun afterTextChanged(s: Editable?) {
                    val email = s.toString().trim()
                    val checkEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    if (checkEmail){
                        binding.activityLogin.tilEmail.isErrorEnabled = false
                        setMyButtonEnable()
                    } else {
                        binding.activityLogin.tilEmail.isErrorEnabled = true
                        binding.activityLogin.tilEmail.error = "Format Email Salah"
                    }
                }
            }
        )

        binding.activityLogin.etPassword.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //do nothing
                }

                override fun afterTextChanged(s: Editable?) {
                    val password = s.toString().trim()
                    if (password.length < 8){
                        binding.activityLogin.passwordInputLayout.isErrorEnabled = true
                        binding.activityLogin.passwordInputLayout.error = "Kata Sandi kurang dari delapan karakter"
                    } else {
                        binding.activityLogin.passwordInputLayout.isErrorEnabled = false
                        setMyButtonEnable()
                    }
                }

            }
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.activityLogin.loadingIndicator.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setMyButtonEnable() {
        val email = binding.activityLogin.etEmailUsername.text.toString().trim()
        val password = binding.activityLogin.etPassword.text.toString().trim()
        binding.activityLogin.btnLogin.isEnabled = ((email.isNotEmpty()) && (password.isNotEmpty()))
    }
}