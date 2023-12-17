package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetDiscussionDetailResponse(

	@field:SerializedName("comments")
	val comments: List<CommentsItem>,

	@field:SerializedName("userProfileImage")
	val userProfileImage: String? = null,

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

data class CommentsItem(

	@field:SerializedName("userProfileImage")
	val userProfileImage: String,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("username")
	val username: String
)

data class CreateCommentResponse(

	@field:SerializedName("comment")
	val comment: CommentsItem,

	@field:SerializedName("message")
	val message: String
)

