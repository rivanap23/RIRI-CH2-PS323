package com.riridev.ririapp.data.model

data class Report(
    val id: Int,
    val title: String,
    val instansi: String,
    val category: String,
    val description: String,
    val location: String,
    val date: String,
    val isDone: Boolean = false
)
