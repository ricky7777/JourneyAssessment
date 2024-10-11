package com.journey.assessment.service

import com.journey.assessment.model.PostModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getPostList(): List<PostModel>
}