package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
