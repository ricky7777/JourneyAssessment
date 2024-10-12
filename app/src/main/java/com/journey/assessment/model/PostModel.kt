package com.journey.assessment.model

/**
 * @author Ricky Chen
 * Every post's data model
 */
data class PostModel(
    val userId: Int,
    val id: Int, // this is postId
    val title: String,
    val body: String
)