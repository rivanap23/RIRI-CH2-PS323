package com.riridev.ririapp.ui.listagensi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.data.dummy.InstansiDummy
import com.riridev.ririapp.databinding.ActivityListAgensiBinding
import com.riridev.ririapp.ui.adapter.ListAgensiAdapter

class ListAgensi : AppCompatActivity() {
    private lateinit var binding: ActivityListAgensiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAgensiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvAgensi.layoutManager = layoutManager

        val adapter = ListAgensiAdapter {
            //do nothing
        }
        adapter.submitList(InstansiDummy.dataInstansi)
        binding.rvAgensi.adapter = adapter
    }
}

