package com.riridev.ririapp.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.riridev.ririapp.R
import com.riridev.ririapp.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setupView()
        object : CountDownTimer(DURATION, INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                // tidak digunakan karena tidak diperlukan ada countdown
            }

            override fun onFinish() {
                navigateToMainActivity()
            }
        }.start()
    }
    private fun setupView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object {
        private const val INTERVAL = 1000L
        private const val DURATION = 3000L
    }
}