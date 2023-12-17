package com.riridev.ririapp.ui.adddiscuss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.DiscussionRepository
import com.riridev.ririapp.data.model.DiscussionModel
import com.riridev.ririapp.data.remote.response.CreateDiscussionResponse
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.launch

class AddDiscussViewModel(
    private val discussionRepository: DiscussionRepository
): ViewModel() {
    private var _addDiscuss = MutableLiveData<Result<CreateDiscussionResponse>> ()
    val addDiscuss: LiveData<Result<CreateDiscussionResponse>> = _addDiscuss

    fun createDiscussion(discussionModel: DiscussionModel){
        _addDiscuss.value = Result.Loading
        viewModelScope.launch {
            _addDiscuss.value = discussionRepository.createDiscussion(discussionModel)
        }
    }
}