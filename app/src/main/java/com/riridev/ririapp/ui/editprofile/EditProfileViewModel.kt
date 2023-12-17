package com.riridev.ririapp.ui.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.remote.response.ErrorResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditProfileViewModel (
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _editProfile = MutableLiveData<Result<ErrorResponse>>()
    val editProflie: LiveData<Result<ErrorResponse>> = _editProfile
    fun editProfile(email: String?, username: String?){
        _editProfile.value = Result.Loading
        viewModelScope.launch {
            delay(500)
            _editProfile.value  = userRepository.updateUserProfile(email, username)
        }
    }
}