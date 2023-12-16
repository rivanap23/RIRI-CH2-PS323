package com.riridev.ririapp.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.riridev.ririapp.data.UserRepository
import com.riridev.ririapp.data.model.ReportModel
import com.riridev.ririapp.data.remote.response.ReportResponse
import com.riridev.ririapp.data.result.Result

class ReportViewModel(
    private val userRepository: UserRepository,
): ViewModel() {
    fun sendReport(reportModel: ReportModel): LiveData<Result<ReportResponse>>{
        return userRepository.createReport(reportModel)
    }
}