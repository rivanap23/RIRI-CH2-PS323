package com.riridev.ririapp.data.di

import android.content.Context
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.local.pref.UserPreferences
import com.riridev.ririapp.data.local.pref.dataStore
import com.riridev.ririapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.accessToken)
        return UserRepository.getInstance(pref, apiService)
    }
}