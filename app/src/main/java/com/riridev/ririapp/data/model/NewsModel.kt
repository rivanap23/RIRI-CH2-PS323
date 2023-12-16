package com.riridev.ririapp.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(
    val id: Int,
    val title: String,
    val dateTime: String,
    val author: String,
    val editor: String,
    @DrawableRes
    val image: Int,
    val description: String,
    val source: String
): Parcelable