package com.riridev.ririapp.data.remote.retrofit

import com.riridev.ririapp.data.remote.response.LoginResponse
import com.riridev.ririapp.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") name: String,
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
}