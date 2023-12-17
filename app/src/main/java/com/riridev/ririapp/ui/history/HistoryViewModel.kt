package com.riridev.ririapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.remote.response.GetReportResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _process = MutableLiveData<Result<GetReportResponse>>()
    val process: LiveData<Result<GetReportResponse>> = _process

    fun getHistoryReport(){
        _process.value = Result.Loading
        viewModelScope.launch {
            delay(500)
            _process.value = userRepository.getHistoryReport()
        }
    }
}