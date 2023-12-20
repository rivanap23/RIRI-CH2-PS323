package com.riridev.ririapp.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Instansi(
    val id: Int,
    val name: String,
    val logoUrl: String,
    @DrawableRes
    val imageRes: Int,
    val contact: Int,
    val desc: String = "",
    val category: String = "",
): Parcelable
