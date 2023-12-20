package com.riridev.ririapp.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.model.ReportModel
import com.riridev.ririapp.data.remote.response.DetectionFakeReportResponse
import com.riridev.ririapp.data.remote.response.ReportResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.launch

class ReportViewModel(
    private val userRepository: UserRepository,
): ViewModel() {
    private var _report = MutableLiveData<Result<ReportResponse>>()
    val report: LiveData<Result<ReportResponse>> = _report

    private var _fakeDetection = MutableLiveData<Result<DetectionFakeReportResponse>>()
    val fakeDetection: LiveData<Result<DetectionFakeReportResponse>> = _fakeDetection
    fun sendReport(reportModel: ReportModel){
        _report.value = Result.Loading
        viewModelScope.launch {
            _report.value = userRepository.createReport(reportModel)
        }
    }

    fun fakeReportDetection(title: String){
        _fakeDetection.value = Result.Loading
        viewModelScope.launch {
            _fakeDetection.value = userRepository.fakeReportDetection(title)
        }
    }
}