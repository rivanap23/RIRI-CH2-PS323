package com.riridev.ririapp.data

import com.google.gson.Gson
import com.riridev.ririapp.data.local.pref.UserModel
import com.riridev.ririapp.data.local.pref.UserPreferences
import com.riridev.ririapp.data.remote.response.ErrorResponse
import com.riridev.ririapp.data.remote.response.LoginResponse
import com.riridev.ririapp.data.remote.response.RegisterResponse
import com.riridev.ririapp.data.remote.retrofit.ApiService
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreferences,
    private val apiService: ApiService,
){
    suspend fun register(
        email: String,
        username: String,
        placeOfBirth: String,
        dateOfBirth: String,
        password: String,
        password2: String,
    ): Result<RegisterResponse>{
        try {
            val registerResponse = apiService.register(email,username,placeOfBirth,dateOfBirth, password, password2)
            return Result.Success(registerResponse)
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            return Result.Error("Login Failed: $errorMessage")
        } catch (e: Exception){
            return (Result.Error("Signal Problem"))
        }
    }
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        try {
            val loginRepsone = apiService.login(email, password)
            return Result.Success(loginRepsone)
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            return Result.Error("Login Failed: $errorMessage")
        } catch (e: Exception){
            return (Result.Error("Signal Problem"))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreferences,
            apiService: ApiService,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}