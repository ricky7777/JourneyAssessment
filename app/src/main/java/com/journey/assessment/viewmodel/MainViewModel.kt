package com.journey.assessment.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.journey.assessment.dao.CommentDao
import com.journey.assessment.model.CommentEntity
import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel
import com.journey.assessment.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ricky Chen
 * this is get post list view model
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val postRepository: PostRepository,
    private val commentDao: CommentDao
) : AndroidViewModel(application) {

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

    fun saveCommentToLocal(commentModel: CommentModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val localComment = commentDao.getCommentById(commentModel.id)
            val updatedComment = if (localComment != null) {
                localComment.copy(
                    body = commentModel.body,
                    lastUpdated = System.currentTimeMillis()
                )
            } else {
                CommentEntity(
                    id = commentModel.id,
                    postId = commentModel.postId,
                    name = commentModel.name,
                    email = commentModel.email,
                    body = commentModel.body,
                    lastUpdated = System.currentTimeMillis()
                )
            }
            commentDao.insertComment(updatedComment)
        }
    }

    fun getCommentsFromLocal(postId: Int, onCommentsLoaded: (List<CommentModel>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val comments = commentDao.getCommentsByPostId(postId).map {
                CommentModel(it.postId, it.id, it.name, it.email, it.body)
            }
            onCommentsLoaded(comments)
        }
    }

    fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}