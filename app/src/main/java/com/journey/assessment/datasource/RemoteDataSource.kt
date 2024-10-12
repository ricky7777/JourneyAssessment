package com.journey.assessment.datasource

import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel
import com.journey.assessment.service.ApiService

/**
 * @author Ricky Chen
 * remote data source
 */
class RemoteDataSource(private val apiService: ApiService) : DataSource {

    override suspend fun getPosts(): List<PostModel> {
        return apiService.getPostList()
    }

    override suspend fun getPostComments(postId: Int): List<CommentModel> {
        return apiService.getPostComments(postId)
    }
}