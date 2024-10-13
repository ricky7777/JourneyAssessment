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
    onNavigateToComments: (Int, String) -> Unit,
    onBackPress: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var posts = remember { mutableStateOf<List<PostModel>?>(emptyList()) }
    var hasInternet by remember { mutableStateOf(mainViewModel.hasInternetConnection()) }
    var screenState = remember { mutableStateOf(ScreenState.LOADING) }

    LaunchedEffect(Unit) {
        if (hasInternet) {
            mainViewModel.fetchPostList { result ->
                posts.value = result
                screenState.value =
                    if (result.isNullOrEmpty()) ScreenState.NO_CONTENT else ScreenState.SHOW_CONTENT
            }
        } else {
            screenState.value = ScreenState.NO_DATA_NO_INTERNET
        }
    }

    val filteredPosts = filterPosts(posts.value, searchQuery)
    BaseScreen(
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        onBackPress = onBackPress,
        showBackButton = false,
        stringResource(R.string.search_hint_post_placeholder)
    ) { innerPadding ->
        ScreenContent(
            screenState = screenState.value,
            content = {
                if (filteredPosts.isNotEmpty()) {
                    MakeColumns(filteredPosts, onNavigateToComments)
                } else {
                    NoContentView()
                }
            },
            onReload = {
                handleReload(mainViewModel, screenState, posts)
            }
        )
    }
}

fun handleReload(
    mainViewModel: MainViewModel,
    screenState: MutableState<ScreenState>,
    posts: MutableState<List<PostModel>?>
) {
    val hasInternet = mainViewModel.hasInternetConnection()
    if (!hasInternet) {
        return
    }

    screenState.value = ScreenState.LOADING
    mainViewModel.fetchPostList { result ->
        posts.value = result
        screenState.value =
            if (result.isNullOrEmpty()) ScreenState.NO_CONTENT else ScreenState.SHOW_CONTENT
    }
}

@Composable
private fun MakeColumns(
    filteredPosts: List<PostModel>,
    onNavigateToComments: (Int, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
fun PostItem(post: PostModel, onNavigateToComments: (Int, String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNavigateToComments(post.id, post.title) }
    ) {

        addTextItemWithColor(R.string.user_id, post.userId.toString(), Color.Yellow)
        addTextItemWithColor(R.string.title, post.title, Color.Green)
        addTextItemWithColor(R.string.message, getTruncatedBody(post.body), Color.Green)
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