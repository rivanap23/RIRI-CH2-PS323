package com.riridev.ririapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.data.local.pref.UserModel
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.LoginLayoutBinding
import com.riridev.ririapp.ui.ViewModelFactory
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

        loginViewModel.login.observe(this){ result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val user = UserModel(
                        result.data.accessToken,
                        true
                    )
                    loginViewModel.saveSession(user)
                    showToast("${result.data.accessToken} ${result.data.refreshToken}")
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
        }
        binding.activityLogin.btnLogin.setOnClickListener {
            val email = binding.activityLogin.etEmailUsername.text.toString()
            val pass = binding.activityLogin.etPassword.text.toString()
            loginViewModel.loginUser(email, pass)
        }
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
                    setMyButtonEnable()
                }
            }
        )

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
                    setMyButtonEnable()
                }

            }
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.activityLogin.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setMyButtonEnable() {
        val email = binding.activityLogin.etEmailUsername.text.toString()
        val password = binding.activityLogin.etPassword.text.toString()
        binding.activityLogin.btnLogin.isEnabled = ((email != null)&& (password != null))
    }
}