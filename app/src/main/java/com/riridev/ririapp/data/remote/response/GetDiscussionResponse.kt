package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class Timestamp(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)

data class GetDiscussionResponseItem(

	@field:SerializedName("comments")
	val comments: Int,

	@field:SerializedName("userProfileImage")
	val userProfileImage: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("postId")
	val postId: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("timestamp")
	val timestamp: Timestamp,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("likes")
	val likes: Int
)
