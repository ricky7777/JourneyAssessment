package com.journey.assessment.repository

import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel
import com.journey.assessment.datasource.DataSource
import javax.inject.Inject

class PostRepository @Inject constructor(private val dataSource: DataSource) {

    suspend fun getPosts(): List<PostModel> {
        return dataSource.getPosts()
    }

    suspend fun getPostComments(postId: Int): List<CommentModel> {
        return dataSource.getPostComments(postId)
    }
}