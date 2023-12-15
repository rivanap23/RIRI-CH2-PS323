package com.riridev.ririapp.ui.listagensi



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.riridev.ririapp.R
import com.riridev.ririapp.data.model.Agensi
import de.hdodenhof.circleimageview.CircleImageView

class DetailAgensi : AppCompatActivity() {

    private lateinit var agensi: Agensi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_agensi)

        // Dapatkan data agensi dari intent
        agensi = intent.getParcelableExtra("agensi")!!

        // Set data agensi ke view
        val imgBckground = findViewById<ImageView>(R.id.img_background)
        Glide.with(this).load(agensi.bgImage).into(imgBckground)

        val imgPhoto = findViewById<CircleImageView>(R.id.detail_img_item)
        Glide.with(this).load(agensi.imageUrl).into(imgPhoto)

        val tvName = findViewById<TextView>(R.id.detail_tv_name)
        tvName.text = agensi.name

        val tvDescription = findViewById<TextView>(R.id.detail_tv_description)
        tvDescription.text = agensi.desc

    }
}