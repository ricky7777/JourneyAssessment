package com.journey.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Ricky Chen
 * Comment store's data class
 */
@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
    val lastUpdated: Long
)