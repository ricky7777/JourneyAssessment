package com.journey.assessment
import com.journey.assessment.datasource.LocalDataSource
import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class LocalDataSourceTest {
    private val localDataSource = LocalDataSource()

    @Test
    fun `test getPosts returns correct data`() = runBlocking {
        // Act
        val posts: List<PostModel> = localDataSource.getPosts()

        // Assert
        assertNotNull(posts)
        assertEquals(3, posts.size)
        assertEquals("Ricky test", posts[0].title)
        assertEquals("Body of post 1", posts[0].body)
    }

    @Test
    fun `test getPostComments returns correct data`() = runBlocking {
        // Act
        val comments: List<CommentModel> = localDataSource.getPostComments(1)

        // Assert
        assertNotNull(comments)
        assertEquals(2, comments.size)
        assertEquals("Ricky", comments[0].name)
        assertEquals("Comment body 1", comments[0].body)
    }

    @Test
    fun `test getPostComments returns correct postId`() = runBlocking {
        // Act
        val comments: List<CommentModel> = localDataSource.getPostComments(2)

        // Assert
        assertNotNull(comments)
        assertEquals(2, comments.size)
        assertEquals(2, comments[0].postId)  // Ensure postId is correct
    }
}