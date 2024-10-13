package com.journey.assessment.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    var comments = remember { mutableStateOf<List<CommentModel>>(emptyList()) }
    var hasInternet by remember { mutableStateOf(mainViewModel.hasInternetConnection()) }
    var screenState = remember { mutableStateOf(ScreenState.LOADING) }

    LaunchedEffect(postId) {
        postId?.let {
            screenState.value = ScreenState.LOADING
            loadLocalComments(postId, mainViewModel, comments, screenState)

            if (hasInternet) {
                loadNetworkComments(postId, mainViewModel, comments, screenState)
            }
        }
    }

    val filteredComments = filterComments(comments, searchQuery)

    BaseScreen(
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        onBackPress = onBackPress,
        content = {
            ScreenContent(
                screenState = screenState.value,
                content = {
                    if (filteredComments.isNotEmpty()) {
                        MakeCommentsColumns(filteredComments)
                    } else {
                        NoContentView()
                    }
                },
                onReload = {
                    handleReload(postId, mainViewModel, screenState, comments)
                }
            )
        }
    )
}

fun loadLocalComments(
    postId: Int,
    mainViewModel: MainViewModel,
    comments: MutableState<List<CommentModel>>,
    screenState: MutableState<ScreenState>
) {
    loadCommentsFromLocal(postId, mainViewModel) { localComments ->
        updateCommentsState(
            localComments,
            ScreenState.NO_DATA_NO_INTERNET,
            ScreenState.SHOW_CONTENT,
            comments,
            screenState
        )
    }
}

fun loadNetworkComments(
    postId: Int,
    mainViewModel: MainViewModel,
    comments: MutableState<List<CommentModel>>,
    screenState: MutableState<ScreenState>
) {
    loadCommentsFromNetwork(postId, mainViewModel) { networkComments ->
        updateCommentsState(
            networkComments,
            ScreenState.NO_CONTENT,
            ScreenState.SHOW_CONTENT,
            comments,
            screenState
        )
    }
}

fun handleReload(
    postId: Int?,
    mainViewModel: MainViewModel,
    screenState: MutableState<ScreenState>,
    comments: MutableState<List<CommentModel>>
) {
    if (!mainViewModel.hasInternetConnection()) {
        return
    }

    screenState.value = ScreenState.LOADING
    postId?.let {
        loadCommentsFromNetwork(it, mainViewModel) { networkComments ->
            updateCommentsState(
                networkComments,
                ScreenState.NO_CONTENT,
                ScreenState.SHOW_CONTENT,
                comments,
                screenState
            )
        }
    }
}

fun updateCommentsState(
    newComments: List<CommentModel>,
    noDataState: ScreenState,
    hasDataState: ScreenState,
    comments: MutableState<List<CommentModel>>,
    screenState: MutableState<ScreenState>
) {
    comments.value = newComments
    screenState.value = if (newComments.isEmpty()) noDataState else hasDataState
}

@Composable
private fun MakeCommentsColumns(
    filteredComments: List<CommentModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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

fun loadCommentsFromLocal(
    postId: Int,
    mainViewModel: MainViewModel,
    onCommentsLoaded: (List<CommentModel>) -> Unit
) {
    mainViewModel.getCommentsFromLocal(postId) { localComments ->
        onCommentsLoaded(localComments)
    }
}

fun loadCommentsFromNetwork(
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

fun filterComments(
    comments: MutableState<List<CommentModel>>?,
    searchQuery: String
): List<CommentModel> {
    return comments?.value?.filter { comment ->
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