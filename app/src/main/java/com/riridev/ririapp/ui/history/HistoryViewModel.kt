package com.riridev.ririapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.remote.response.GetAcceptedReportsResponse
import com.riridev.ririapp.data.remote.response.GetRejectedReportsResponse
import com.riridev.ririapp.data.remote.response.GetVerifAndProcessReportsResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _process = MutableLiveData<Result<GetVerifAndProcessReportsResponse>>()
    val process: LiveData<Result<GetVerifAndProcessReportsResponse>> = _process

    private var _done = MutableLiveData<Result<GetAcceptedReportsResponse>>()
    val done: LiveData<Result<GetAcceptedReportsResponse>> = _done

    private var _reject = MutableLiveData<Result<GetRejectedReportsResponse>>()
    val reject: LiveData<Result<GetRejectedReportsResponse>> = _reject

    fun getVerifReport(){
        _process.value = Result.Loading
        viewModelScope.launch {
            delay(500)
            _process.value = userRepository.getVerifReport()
        }
    }

    fun getProcessedReport(){
        _process.value = Result.Loading
        viewModelScope.launch {
            delay(500)
            _process.value = userRepository.getProcessedReport()
        }
    }

    fun getRejectedReport(){
        _reject.value = Result.Loading
        viewModelScope.launch {
            delay(500)
            _reject.value = userRepository.getRejectedReport()
        }
    }

    fun getDoneReport(){
        _done.value = Result.Loading
        viewModelScope.launch {
            delay(500)
            _done.value = userRepository.getDoneReport()
        }
    }
}