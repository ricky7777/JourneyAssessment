package com.journey.assessment.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.journey.assessment.R
import com.journey.assessment.model.PostModel
import com.journey.assessment.viewmodel.MainViewModel

/**
 * @author Ricky Chen
 * Post main screen
 * responsible show post list and navigate to comments detail screen
 */
@Composable
fun PostScreen(
    onNavigateToComments: (Int) -> Unit,
    onBackPress: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var posts by remember { mutableStateOf<List<PostModel>?>(emptyList()) }

    LaunchedEffect(Unit) {
        mainViewModel.fetchPostList { result ->
            posts = result
        }
    }

    val filteredPosts = filterPosts(posts, searchQuery)
    BaseScreen(
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        onBackPress = onBackPress,
        showBackButton = false
    ) { innerPadding ->
        if (filteredPosts.isNotEmpty()) {
            MakeColumns(innerPadding, filteredPosts, onNavigateToComments)
        } else {
            Text(
                text = stringResource(id = R.string.no_posts),
            )
        }
    }
}

@Composable
private fun MakeColumns(
    innerPadding: PaddingValues,
    filteredPosts: List<PostModel>,
    onNavigateToComments: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filteredPosts) { post ->
            PostItem(post = post, onNavigateToComments = onNavigateToComments)
            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun PostItem(post: PostModel, onNavigateToComments: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNavigateToComments(post.id) }
    ) {
        Text(text = stringResource(id = R.string.user_id, post.userId))
        Text(text = stringResource(id = R.string.title, post.title))
        Text(text = stringResource(id = R.string.message, getTruncatedBody(post.body)))
    }
}

fun filterPosts(posts: List<PostModel>?, searchQuery: String): List<PostModel> {
    return posts?.filter { post ->
        post.title.contains(searchQuery, ignoreCase = true)
    } ?: emptyList()
}

fun getTruncatedBody(body: String): String {
    return if (body.length > 100) {
        body.substring(0, 100) + "..."
    } else {
        body
    }
}