package com.journey.assessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel
import com.journey.assessment.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ricky Chen
 * this is get post list view model
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    fun fetchPostList(onResult: (List<PostModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val posts = postRepository.getPosts()
                onResult(posts)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }
    }

    fun fetchPostComments(postId: Int, onResult: (List<CommentModel>) -> Unit) {
        viewModelScope.launch {
            val comments = postRepository.getPostComments(postId)
            onResult(comments)
        }
    }
}