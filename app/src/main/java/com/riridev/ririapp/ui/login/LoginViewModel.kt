package com.riridev.ririapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.local.pref.UserModel
import com.riridev.ririapp.data.remote.response.LoginResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _login = MutableLiveData<Result<LoginResponse>>()
    val login: LiveData<Result<LoginResponse>> = _login


    fun loginUser(email: String, password: String) {
        _login.value = Result.Loading
        viewModelScope.launch {
            _login.value = userRepository.login(email, password)
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
}