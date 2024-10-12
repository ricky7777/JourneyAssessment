package com.journey.assessment.screen

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
import com.journey.assessment.model.CommentModel
import com.journey.assessment.viewmodel.MainViewModel

/**
 * @author Ricky Chen
 * show special post id's comments
 */
@Composable
fun CommentsDetailScreen(
    postId: Int?,
    onBackPress: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf<List<CommentModel>>(emptyList()) }

    LaunchedEffect(postId) {
        postId?.let {
            handleCommentsLoading(postId, mainViewModel) { loadedComments ->
                comments = loadedComments
            }
        }
    }

    val filteredComments = filterComments(comments, searchQuery)

    BaseScreen(
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        onBackPress = onBackPress
    ) { innerPadding ->
        if (filteredComments.isNotEmpty()) {
            MakeColumns(innerPadding, filteredComments)
        } else {
            Text(
                text = stringResource(id = R.string.no_comments),
            )
        }
    }
}

suspend fun handleCommentsLoading(
    postId: Int,
    mainViewModel: MainViewModel,
    onCommentsLoaded: (List<CommentModel>) -> Unit
) {
    if (mainViewModel.hasInternetConnection()) {
        loadCommentsFromLocal(postId, mainViewModel, onCommentsLoaded)
        loadCommentsFromNetwork(postId, mainViewModel, onCommentsLoaded)
    } else {
        loadCommentsFromLocal(postId, mainViewModel, onCommentsLoaded)
    }
}

suspend fun loadCommentsFromLocal(
    postId: Int,
    mainViewModel: MainViewModel,
    onCommentsLoaded: (List<CommentModel>) -> Unit
) {
    mainViewModel.getCommentsFromLocal(postId) { localComments ->
        if (localComments.isNotEmpty()) {
            onCommentsLoaded(localComments)
        }
    }
}

suspend fun loadCommentsFromNetwork(
    postId: Int,
    mainViewModel: MainViewModel,
    onCommentsLoaded: (List<CommentModel>) -> Unit
) {
    mainViewModel.fetchPostComments(postId) { result ->
        onCommentsLoaded(result)

        result.forEach { comment ->
            mainViewModel.saveCommentToLocal(comment)
        }
    }
}

@Composable
private fun MakeColumns(
    innerPadding: PaddingValues,
    filteredComments: List<CommentModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filteredComments) { comment ->
            CommentItem(comment)
            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

fun filterComments(comments: List<CommentModel>?, searchQuery: String): List<CommentModel> {
    return comments?.filter { comment ->
        comment.name.contains(searchQuery, ignoreCase = true)
    } ?: emptyList()
}

@Composable
fun CommentItem(comment: CommentModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = stringResource(id = R.string.comment_name, comment.name))
        Text(text = stringResource(id = R.string.comment_email, comment.email))
        Text(text = stringResource(id = R.string.comment_body, comment.body))
    }
}