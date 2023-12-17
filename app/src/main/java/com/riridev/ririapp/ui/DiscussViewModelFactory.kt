package com.riridev.ririapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riridev.ririapp.data.DiscussionRepository
import com.riridev.ririapp.data.di.Injection
import com.riridev.ririapp.ui.adddiscuss.AddDiscussViewModel
import com.riridev.ririapp.ui.detaildiscuss.DetailDiscussViewModel
import com.riridev.ririapp.ui.discuss.DisscussViewModel

class DiscussViewModelFactory(private val repository: DiscussionRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddDiscussViewModel::class.java) -> {
                AddDiscussViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DisscussViewModel::class.java) -> {
                DisscussViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailDiscussViewModel::class.java) -> {
                DetailDiscussViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DiscussViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): DiscussViewModelFactory {
            if (INSTANCE == null) {
                synchronized(DiscussViewModelFactory::class.java) {
                    INSTANCE = DiscussViewModelFactory(Injection.provideDiscussionRepository(context))
                }
            }
            return INSTANCE as DiscussViewModelFactory
        }
    }

}