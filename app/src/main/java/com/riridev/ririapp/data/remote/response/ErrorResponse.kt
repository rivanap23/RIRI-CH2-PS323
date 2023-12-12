package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("error")
    val message: String
)