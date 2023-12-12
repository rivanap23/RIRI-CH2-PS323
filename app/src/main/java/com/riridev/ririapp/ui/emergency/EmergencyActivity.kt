package com.riridev.ririapp.ui.emergency

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.data.dummy.InstansiDummy
import com.riridev.ririapp.databinding.ActivityEmergencyBinding
import com.riridev.ririapp.ui.adapter.EmergencyAdapter

class EmergencyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        binding.rvEmergency.layoutManager = layoutManager

        val adapter = EmergencyAdapter {
            val phoneNumber = it.contact.toString()
            val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(dialPhoneIntent)
        }
        adapter.submitList(InstansiDummy.dataInstansi)
        binding.rvEmergency.adapter = adapter
    }
}