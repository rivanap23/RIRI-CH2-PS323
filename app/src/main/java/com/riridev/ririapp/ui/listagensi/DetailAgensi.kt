package com.riridev.ririapp.ui.listagensi



import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.riridev.ririapp.R
import com.riridev.ririapp.data.model.Instansi
import com.riridev.ririapp.databinding.ActivityDetailAgensiBinding


class DetailAgensi : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAgensiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAgensiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topAppBar.title = " "
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        @Suppress("DEPRECATION")
        val agensi = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("AGENSI_DATA", Instansi::class.java)
        } else {
            intent.getParcelableExtra("AGENSI_DATA")
        }

        if (agensi != null){
            setupView(agensi)
        }
    }

    private fun setupView(agensi: Instansi) {
        Glide.with(binding.root)
            .load(agensi.imageRes)
            .into(binding.ivBgInstansi)
        Glide.with(binding.root)
            .load(agensi.logoUrl)
            .placeholder(ContextCompat.getDrawable(this, R.drawable.baseline_account_circle_24))
            .into(binding.circleImageViewInstansi)

        binding.tvTitleDetailInstansi.text = agensi.name
        binding.tvDescriptionInstansi.text = agensi.desc
        binding.chipKategori.text = agensi.category
    }
}
