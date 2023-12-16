package com.riridev.ririapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.riridev.ririapp.data.local.pref.UserModel
import com.riridev.ririapp.data.local.pref.UserPreferences
import com.riridev.ririapp.data.model.ReportModel
import com.riridev.ririapp.data.remote.response.ErrorResponse
import com.riridev.ririapp.data.remote.response.LoginResponse
import com.riridev.ririapp.data.remote.response.RegisterResponse
import com.riridev.ririapp.data.remote.response.ReportResponse
import com.riridev.ririapp.data.remote.response.UploadProfilePictureResponse
import com.riridev.ririapp.data.remote.response.UserResponse
import com.riridev.ririapp.data.remote.retrofit.ApiService
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreferences,
    private val apiService: ApiService,
) {
    suspend fun register(
        email: String,
        username: String,
        placeOfBirth: String,
        dateOfBirth: String,
        password: String,
        password2: String,
    ): Result<RegisterResponse> {
        return try {
            val registerResponse =
                apiService.register(email, username, placeOfBirth, dateOfBirth, password, password2)
            Result.Success(registerResponse)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error("Login Failed: $errorMessage")
        } catch (e: Exception) {
            (Result.Error("Signal Problem"))
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val loginResponse = apiService.login(email, password)
            Result.Success(loginResponse)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error("Login Failed: $errorMessage")
        } catch (e: Exception) {
            (Result.Error("Signal Problem"))
        }
    }

    fun getUserDetail(): LiveData<Result<UserResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val user = getSession().first()
                Log.d("TAG", "getUserDetail: $user")
                val userResponse = apiService.getUserDetail(user.userId)
                emit(Result.Success(userResponse))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error("Failed: $errorMessage"))
            }
        }
    }

    fun uploadProfilePicture(file: File): LiveData<Result<UploadProfilePictureResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val multipartBody =
                    MultipartBody.Part.createFormData(
                        "file",
                        file.name,
                        requestFile,
                    )
                val user = userPreference.getSession().first()
                val uploadResponse = apiService.uploadProfileImage(user.userId, multipartBody)
                emit(Result.Success(uploadResponse))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error("Upload Failed: $errorMessage"))
            }
        }
    }

    suspend fun updateUserProfile(email: String?, username: String?): Result<ErrorResponse> {
        return try {
            val user = userPreference.getSession().first()
            if (email == null) {
                val updateResponse =
                    apiService.updateProflieInfo(userId = user.userId, null, username.toString())
                Result.Success(updateResponse)
            } else if (username == null) {
                val updateResponse =
                    apiService.updateProflieInfo(userId = user.userId, email.toString(), null)
                Result.Success(updateResponse)
            } else {
                val updateResponse = apiService.updateProflieInfo(
                    userId = user.userId,
                    email.toString(),
                    username.toString()
                )
                Result.Success(updateResponse)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error("Upload Failed: $errorMessage")
        }
    }


    fun createReport(
        report: ReportModel
    ): LiveData<Result<ReportResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val user = userPreference.getSession().first()
                val reqTitle = report.judulLaporan.toRequestBody("text/plain".toMediaType())
                val reqDescription =
                    report.deskripsiLaporan.toRequestBody("text/plain".toMediaType())
                val reqCategory = report.kategoriLaporan.toRequestBody("text/plain".toMediaType())
                val reqInstansi = report.instansi.toRequestBody("text/plain".toMediaType())
                val reqLocation = report.lokasi.toRequestBody("text/plain".toMediaType())
                val reqDetailLocation =
                    report.detailLokasi.toRequestBody("text/plain".toMediaType())
                val requestFile = report.file.asRequestBody("image/jpeg".toMediaType())
                val multipartBody =
                    MultipartBody.Part.createFormData(
                        "file",
                        report.file.name,
                        requestFile,
                    )
                val reportResponse = apiService.createReport(
                    user.userId,
                    reqTitle,
                    reqInstansi,
                    reqCategory,
                    reqDescription,
                    reqLocation,
                    reqDetailLocation,
                    multipartBody
                )
                emit(Result.Success(reportResponse))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error("Upload Failed: $errorMessage"))
            }
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