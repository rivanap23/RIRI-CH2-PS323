package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("message")
	val message: String
)
