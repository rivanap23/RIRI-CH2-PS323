package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetectionFakeReportResponse(

	@field:SerializedName("prediction")
	val prediction: Int,

	@field:SerializedName("status")
	val status: String
)
