package com.journey.assessment.datasource

import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel
import org.json.JSONArray

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

        val jsonArray = JSONArray(jsonString)
        val posts = mutableListOf<PostModel>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val post = PostModel(
                id = jsonObject.getInt("id"),
                userId = jsonObject.getInt("userId"),
                title = jsonObject.getString("title"),
                body = jsonObject.getString("body")
            )
            posts.add(post)
        }
        return posts
    }

    override suspend fun getPostComments(postId: Int): List<CommentModel> {
        val jsonString = """
            [
                {"id": 1, "postId": $postId, "name": "Ricky", "email": "risk0917@test.com", "body": "Comment body 1"},
                {"id": 2, "postId": $postId, "name": "Commenter 2", "email": "email2@test.com", "body": "Comment body 2"}
            ]
        """

        val jsonArray = JSONArray(jsonString)
        val comments = mutableListOf<CommentModel>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val comment = CommentModel(
                id = jsonObject.getInt("id"),
                postId = jsonObject.getInt("postId"),
                name = jsonObject.getString("name"),
                email = jsonObject.getString("email"),
                body = jsonObject.getString("body")
            )
            comments.add(comment)
        }
        return comments
    }
}