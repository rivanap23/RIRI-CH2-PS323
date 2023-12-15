package com.riridev.ririapp.ui.listagensi



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.riridev.ririapp.data.model.Agensi
import com.riridev.ririapp.databinding.ActivityDetailAgensiBinding


class DetailAgensi : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAgensiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAgensiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val agensi = intent.getSerializableExtra("AGENSI_DATA") as Agensi

       Glide.with(this).load(agensi.bgImage).into(binding.imgBackground)
        Glide.with(this).load(agensi.imageUrl).into(binding.detailImgItem)
        binding.detailTvName.text = agensi.name
        binding.tvItemDescription.text = agensi.desc

    }
}
