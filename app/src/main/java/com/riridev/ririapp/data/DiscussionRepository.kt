package com.riridev.ririapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.riridev.ririapp.data.local.pref.UserPreferences
import com.riridev.ririapp.data.model.DiscussionModel
import com.riridev.ririapp.data.remote.response.CreateCommentResponse
import com.riridev.ririapp.data.remote.response.CreateDiscussionResponse
import com.riridev.ririapp.data.remote.response.ErrorResponse
import com.riridev.ririapp.data.remote.response.GetDiscussionDetailResponse
import com.riridev.ririapp.data.remote.response.GetDiscussionResponseItem
import com.riridev.ririapp.data.remote.retrofit.ApiService
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class DiscussionRepository private constructor(
    private val userPreference: UserPreferences,
    private val apiService: ApiService,
) {

    suspend fun createDiscussion(
        discussionModel: DiscussionModel
    ): Result<CreateDiscussionResponse>{
        return try {
            val user = runBlocking { userPreference.getSession().first() }
            val reqTitle = discussionModel.title.toRequestBody("text/plain".toMediaType())
            val reqContent = discussionModel.content.toRequestBody("text/plain".toMediaType())
            val requestFile = discussionModel.file.asRequestBody("image/jpeg".toMediaType())
            val multipartBody =
                MultipartBody.Part.createFormData(
                    "file",
                    discussionModel.file.name,
                    requestFile,
                )
            val createDiscussionResponse = apiService.createDiscussion(
                user.userId,
                reqTitle,
                reqContent,
                multipartBody
            )
            Result.Success(createDiscussionResponse)
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error("Upload Failed: $errorMessage")
        }
    }

    suspend fun getAllDiscussion(): Result<List<GetDiscussionResponseItem>>{
        return try {
            val getDiscussionResponse = apiService.getAllDiscussion()
            Result.Success(getDiscussionResponse)
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error(errorMessage)
        }
    }

     fun getDetailDiscussion(postId: String): LiveData<Result<GetDiscussionDetailResponse>>{
        return liveData {
            emit(Result.Loading)
            try {
                val getDiscussionDetailResponse = apiService.getDiscussionDetail(postId)
                emit(Result.Success(getDiscussionDetailResponse))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage))
            }
        }
    }


    fun createCommentUser(postId: String, comment: String): LiveData<Result<CreateCommentResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val user = runBlocking { userPreference.getSession().first() }
                val response = apiService.createCommentUser(postId, user.username, comment)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage))
            }
        }
    }

    fun addLikeDiscussion(postId: String): LiveData<Result<ErrorResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val user = runBlocking { userPreference.getSession().first() }
                val response = apiService.likeDiscussion(postId, user.username)
                emit(Result.Success(response))
            }  catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: DiscussionRepository? = null

        fun getInstance(
            userPreference: UserPreferences,
            apiService: ApiService,
        ): DiscussionRepository =
            instance ?: synchronized(this) {
                instance ?: DiscussionRepository(userPreference, apiService)
            }.also { instance = it }
    }
}