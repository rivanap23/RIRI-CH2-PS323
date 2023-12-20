package com.riridev.ririapp.data.remote.retrofit.apiml

import com.google.gson.JsonObject
import com.riridev.ririapp.data.remote.response.DetectionFakeReportResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiMlService {
    @POST("predict-fake")
    suspend fun predictFakeReport(
        @Body text: JsonObject
    ): DetectionFakeReportResponse

}