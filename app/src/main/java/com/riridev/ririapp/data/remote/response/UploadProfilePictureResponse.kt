package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UploadProfilePictureResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("url")
	val url: String
)




