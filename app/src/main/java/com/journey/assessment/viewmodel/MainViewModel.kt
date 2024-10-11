package com.journey.assessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.assessment.model.PostModel
import com.journey.assessment.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ricky Chen
 * this is get post list view model
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    fun fetchPostList(onResult: (List<PostModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val post = apiService.getPostList()
                onResult(post)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }
    }
}