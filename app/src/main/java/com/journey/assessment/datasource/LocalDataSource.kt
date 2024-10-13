package com.journey.assessment.datasource

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel

/**
 * @author Ricky Chen
 * local data source is for test and demo another data source
 */
class LocalDataSource : DataSource {

    override suspend fun getPosts(): List<PostModel> {
        val jsonString = """
            [
                {"id": 1, "userId": 1, "title": "Ricky test", "body": "Body of post 1"},
                {"id": 2, "userId": 1, "title": "Post 2", "body": "Body of post 2"},
                {"id": 3, "userId": 2, "title": "Post 3", "body": "Body of post 3"}
            ]
        """
        val listType = object : TypeToken<List<PostModel>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    override suspend fun getPostComments(postId: Int): List<CommentModel> {
        val jsonString = """
            [
                {"id": 1, "postId": $postId, "name": "Ricky", "email": "risk0917@test.com", "body": "Comment body 1"},
                {"id": 2, "postId": $postId, "name": "Commenter 2", "email": "email2@test.com", "body": "Comment body 2"}
            ]
        """
        val listType = object : TypeToken<List<CommentModel>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}