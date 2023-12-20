package com.riridev.ririapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetVerifAndProcessReportsResponse(

	@field:SerializedName("processedReports")
	val processedReports: List<ProcessedReportsItem>,

	@field:SerializedName("message")
	val message: String
)

data class GetAcceptedReportsResponse(
	@field:SerializedName("acceptedReports")
	val processedReports: List<ProcessedReportsItem>,

	@field:SerializedName("message")
	val message: String
)

data class GetRejectedReportsResponse(
	@field:SerializedName("rejectedReports")
	val processedReports: List<ProcessedReportsItem>,

	@field:SerializedName("message")
	val message: String
)

data class CreatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)

data class ProcessedReportsItem(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt,

	@field:SerializedName("judulLaporan")
	val judulLaporan: String,

	@field:SerializedName("buktiFoto")
	val buktiFoto: String,

	@field:SerializedName("reportId")
	val reportId: String,

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("kategoriLaporan")
	val kategoriLaporan: String,

	@field:SerializedName("deskripsiLaporan")
	val deskripsiLaporan: String,

	@field:SerializedName("instansi")
	val instansi: String,

	@field:SerializedName("detailLokasi")
	val detailLokasi: String,

	@field:SerializedName("status")
	val status: String
)
