package com.riridev.ririapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.remote.response.UploadProfilePictureResponse
import com.riridev.ririapp.data.remote.response.UserResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _profileDetail = MutableLiveData<Result<UserResponse>>()
    val profileDetail: LiveData<Result<UserResponse>> = _profileDetail

    fun getUserProfile(): LiveData<Result<UserResponse>> {
        return userRepository.getUserDetail()
    }

    fun updateProfilePicture(file: File): LiveData<Result<UploadProfilePictureResponse>>{
        return userRepository.uploadProfilePicture(file)
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}