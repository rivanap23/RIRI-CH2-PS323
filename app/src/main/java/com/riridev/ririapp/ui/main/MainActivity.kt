package com.riridev.ririapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.riridev.ririapp.R
import com.riridev.ririapp.databinding.ActivityMainBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.chat.ChatFragment
import com.riridev.ririapp.ui.home.HomeFragment
import com.riridev.ririapp.ui.login.LoginActivity
import com.riridev.ririapp.ui.notification.NotificationFragment
import com.riridev.ririapp.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getSession().observe(this){user ->
            if (!user.isLogin) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.mainActivity.visibility = View.VISIBLE
            }
        }
        replaceFragment(HomeFragment())
        navigationBottom()
    }
    private fun navigationBottom() {
        binding.bottomNavigationBar.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.item_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.item_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.item_chat -> {
                    replaceFragment(ChatFragment())
                    true
                }
                R.id.item_notif -> {
                    replaceFragment(NotificationFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}