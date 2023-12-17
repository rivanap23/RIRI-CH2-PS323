package com.riridev.ririapp.data.model

import java.io.File

data class ReportModel(
    val judulLaporan: String,
    val instansi: String,
    val kategoriLaporan: String,
    val deskripsiLaporan: String,
    val lokasi: String,
    val detailLokasi: String,
    val file: File
)