package com.riridev.ririapp.data.di

import android.content.Context
import com.riridev.ririapp.data.DiscussionRepository
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.local.pref.UserPreferences
import com.riridev.ririapp.data.local.pref.dataStore
import com.riridev.ririapp.data.remote.retrofit.ApiConfig
import com.riridev.ririapp.data.remote.retrofit.apiml.ApiMlConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val apiMlService = ApiMlConfig.getApiMlService()
        return UserRepository.getInstance(pref, apiService, apiMlService)
    }

    fun provideDiscussionRepository(context: Context): DiscussionRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return DiscussionRepository.getInstance(pref, apiService)
    }
}