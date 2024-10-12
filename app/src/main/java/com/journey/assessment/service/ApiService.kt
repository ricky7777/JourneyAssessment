package com.journey.assessment.service

import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getPostList(): List<PostModel>

    @GET("posts/{postId}/comments")
    suspend fun getPostComments(@Path("postId") postId: Int): List<CommentModel>
}