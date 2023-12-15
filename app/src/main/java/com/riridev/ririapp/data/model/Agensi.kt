package com.riridev.ririapp.data.model

import java.io.Serializable


data class Agensi(
    val id: Int,
    val name: String,
    val bgImage: String ,
    val imageUrl: String ,
    val desc: String
) : Serializable
