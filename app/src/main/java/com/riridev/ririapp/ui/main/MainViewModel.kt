package com.riridev.ririapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.local.pref.UserModel

class MainViewModel(
    private val userRepository: UserRepository,
) : ViewModel(){
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}