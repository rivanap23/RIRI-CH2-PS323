package com.riridev.ririapp.ui.detaildiscuss

import androidx.lifecycle.ViewModel
import com.riridev.ririapp.data.DiscussionRepository


class DetailDiscussViewModel(
    private val discussionRepository: DiscussionRepository
) : ViewModel() {
    fun getDetailDiscussion(postId: String) = discussionRepository.getDetailDiscussion(postId)
    fun createCommentUser(postId: String, comment: String) = discussionRepository.createCommentUser(postId, comment)
    fun addLikeDiscussion(postId: String) = discussionRepository.addLikeDiscussion(postId)
}