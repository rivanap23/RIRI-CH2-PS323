package com.riridev.ririapp.data.model

import java.io.File

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

data class ReportModel(
    val judulLaporan: String,
    val instansi: String,
    val kategoriLaporan: String,
    val deskripsiLaporan: String,
    val lokasi: String,
    val detailLokasi: String,
    val file: File,
    val isDone: Boolean = false
)