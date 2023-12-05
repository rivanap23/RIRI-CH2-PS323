package com.riridev.ririapp.data.model

data class Instansi(
    val id: Int,
    val name: String,
    val logoUrl: String,
    val imageUrl: String = "",
    val contact: Int
)
