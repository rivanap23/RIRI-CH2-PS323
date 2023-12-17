package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreateDiscussionResponse(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("postId")
	val postId: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
