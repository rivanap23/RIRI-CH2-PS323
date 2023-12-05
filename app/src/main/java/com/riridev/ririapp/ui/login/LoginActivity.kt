package com.riridev.ririapp.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.databinding.LoginLayoutBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityLogin.etEmailUsername.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //
                }

                override fun afterTextChanged(s: Editable?) {
                    setMyButtonEnable()
                }

            }
        )
    }


    private fun setMyButtonEnable() {
        val result = binding.activityLogin.etEmailUsername.text
        binding.activityLogin.btnLogin.isEnabled = result != null && result.toString().isNotEmpty()
    }

}