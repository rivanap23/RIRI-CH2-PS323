package com.riridev.ririapp.ui.discuss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riridev.ririapp.data.DiscussionRepository
import com.riridev.ririapp.data.remote.response.GetDiscussionResponseItem
import com.riridev.ririapp.data.result.Result
import kotlinx.coroutines.launch

class DisscussViewModel(
    private val discussionRepository: DiscussionRepository
) : ViewModel() {
    private var _getDiscussion = MutableLiveData<Result<List<GetDiscussionResponseItem>>>()
    val getDiscussion: LiveData<Result<List<GetDiscussionResponseItem>>> = _getDiscussion

    fun getAllDiscussion(){
        _getDiscussion.value = Result.Loading
        viewModelScope.launch {
            _getDiscussion.value = discussionRepository.getAllDiscussion()
        }
    }

    fun addLikeDiscussion(postId: String) = discussionRepository.addLikeDiscussion(postId)
}