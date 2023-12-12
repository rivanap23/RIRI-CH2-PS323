package com.riridev.ririapp.data.model

data class Discuss(
    val id: Int,
    val nama: String,
    val profilePic: String = "",
    val title: String,
    val date: String,
    val isi: String,
    val imageUrl: String = ""
)
