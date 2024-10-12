package com.journey.assessment.model

/**
 * @author Ricky Chen
 * Every comment Model
 */
data class CommentModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)