package com.journey.assessment.datasource

import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel

/**
 * @author Ricky Chen
 * A data source interface,
 * use this data source interface to rule any data source
 */
interface DataSource {
    suspend fun getPosts(): List<PostModel>
    suspend fun getPostComments(postId: Int): List<CommentModel>
}