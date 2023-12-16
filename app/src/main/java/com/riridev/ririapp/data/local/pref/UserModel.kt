package com.riridev.ririapp.data.local.pref

data class UserModel(
    val userId: String,
    val username: String,
    val accessToken: String,
    val isLogin: Boolean = false,
)