package com.riridev.ririapp.ui.editprofile

import androidx.lifecycle.ViewModel
import com.riridev.ririapp.data.UserRepository

class EditProfileViewModel (
    private val userRepository: UserRepository,
) : ViewModel() {
    fun editUsername(username: String) = userRepository.updateUsername(username)
    fun editEmail(email: String) = userRepository.updateEmail(email)

    fun updateProfileInfo(email: String, username: String) = userRepository.updateProfileInfo(email, username)
}