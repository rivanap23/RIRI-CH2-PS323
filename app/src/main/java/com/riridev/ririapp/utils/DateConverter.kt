package com.riridev.ririapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateConverter {
    fun convertMillisToString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun getDateString(seconds: Long, outputPattern: String): String {
        return try {
            val dateFormat = SimpleDateFormat(outputPattern, Locale.ENGLISH)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = seconds * 1000
            val date = calendar.time
            dateFormat.format(date)
        } catch (e: Exception) {
            Log.e("utils", "Date format", e)
            ""
        }
    }
}