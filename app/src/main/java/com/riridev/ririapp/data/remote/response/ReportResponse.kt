package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReportResponse(

	@field:SerializedName("reportId")
	val reportId: String,

	@field:SerializedName("message")
	val message: String
)
