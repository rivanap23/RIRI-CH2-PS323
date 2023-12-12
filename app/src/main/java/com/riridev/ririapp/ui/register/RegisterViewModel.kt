package com.riridev.ririapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.remote.response.RegisterResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.launch

class RegisterViewModel (
    private val userRepository: UserRepository,
): ViewModel(){
    private var _register = MutableLiveData<Result<RegisterResponse>>()
    val register: LiveData<Result<RegisterResponse>> = _register

    fun register(
        email: String,
        username: String,
        placeOfBirth: String,
        dateOfBirth: String,
        password: String,
        password2: String
    ){
        _register.value = Result.Loading
        viewModelScope.launch {
            _register.value = userRepository.register(email, username, placeOfBirth, dateOfBirth, password, password2)
        }
    }
}