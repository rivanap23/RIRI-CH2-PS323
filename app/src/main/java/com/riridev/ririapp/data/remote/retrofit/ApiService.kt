package com.riridev.ririapp.data.remote.retrofit

import com.riridev.ririapp.data.remote.response.CreateCommentResponse
import com.riridev.ririapp.data.remote.response.CreateDiscussionResponse
import com.riridev.ririapp.data.remote.response.ErrorResponse
import com.riridev.ririapp.data.remote.response.GetDiscussionDetailResponse
import com.riridev.ririapp.data.remote.response.GetDiscussionResponseItem
import com.riridev.ririapp.data.remote.response.GetReportResponse
import com.riridev.ririapp.data.remote.response.LoginResponse
import com.riridev.ririapp.data.remote.response.RegisterResponse
import com.riridev.ririapp.data.remote.response.ReportResponse
import com.riridev.ririapp.data.remote.response.UploadProfilePictureResponse
import com.riridev.ririapp.data.remote.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    //auth
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("placeOfBirth") placeOfBirth: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("password") password: String,
        @Field("password2") password2: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") name: String,
        @Field("password") password: String,
    ): LoginResponse


    //profile
    @Multipart
    @POST("{userId}/update-prof-image")
    suspend fun uploadProfileImage(
        @Path("userId") userId: String,
        @Part file: MultipartBody.Part,
    ): UploadProfilePictureResponse

    @FormUrlEncoded
    @PUT("{userId}/edit-profile/edit")
    suspend fun updateProflieInfo(
        @Path("userId") userId: String,
        @Field("email") email: String?,
        @Field("username") username: String?,
    ): ErrorResponse


    @GET("{userId}/user-detail")
    suspend fun getUserDetail(
        @Path("userId") userId: String,
    ): UserResponse

    //report
    @Multipart
    @POST("{userId}/report/create-report")
    suspend fun createReport(
        @Path("userId") userId: String,
        @Part("judulLaporan") judulLaporan: RequestBody,
        @Part("instansi") instansi: RequestBody,
        @Part("kategoriLaporan") kategoriLaporan: RequestBody,
        @Part("deskripsiLaporan") deskripsiLaporan: RequestBody,
        @Part("lokasi") lokasi: RequestBody,
        @Part("detailLokasi") detailLokasi: RequestBody,
        @Part file: MultipartBody.Part,
    ): ReportResponse

    @GET("{userId}/report/report-history/diproses")
    suspend fun getReport(
        @Path("userId") userId: String,
    ): GetReportResponse

    //discussion
    @Multipart
    @POST("{userId}/discussions/create-discussion")
    suspend fun createDiscussion(
        @Path("userId") userId: String,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part file: MultipartBody.Part,
    ): CreateDiscussionResponse

    @GET("/discussions")
    suspend fun getAllDiscussion(): List<GetDiscussionResponseItem>

    @GET("/discussions/{postId}")
    suspend fun getDiscussionDetail(
        @Path("postId") postId: String
    ): GetDiscussionDetailResponse

    @FormUrlEncoded
    @POST("discussions/{postId}/comment")
    suspend fun createCommentUser(
        @Path("postId") postId: String,
        @Field("username") username: String,
        @Field("comment") comment: String,
    ): CreateCommentResponse

    @FormUrlEncoded
    @POST("discussions/{postId}/like")
    suspend fun likeDiscussion(
        @Path("postId") postId: String,
        @Field("username") username: String,
    ): ErrorResponse
}