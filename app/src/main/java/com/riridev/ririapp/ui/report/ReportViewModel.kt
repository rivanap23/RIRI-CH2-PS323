package com.riridev.ririapp.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.model.ReportModel
import com.riridev.ririapp.data.remote.response.ReportResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.launch

class ReportViewModel(
    private val userRepository: UserRepository,
): ViewModel() {
    private var _report = MutableLiveData<Result<ReportResponse>>()
    val report: LiveData<Result<ReportResponse>> = _report
    fun sendReport(reportModel: ReportModel){
        _report.value = Result.Loading
        viewModelScope.launch {
            _report.value = userRepository.createReport(reportModel)
        }
    }
}