package com.riridev.ririapp.ui.emergency

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

        val layoutManager = LinearLayoutManager(this)
        binding.rvEmergency.layoutManager = layoutManager

        val adapter = EmergencyAdapter {
            //do nothing
        }
        adapter.submitList(InstansiDummy.dataInstansi)
        binding.rvEmergency.adapter = adapter
    }
}