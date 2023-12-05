package com.riridev.ririapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.riridev.ririapp.R
import com.riridev.ririapp.databinding.ActivityMainBinding
import com.riridev.ririapp.ui.home.HomeFragment
import com.riridev.ririapp.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}