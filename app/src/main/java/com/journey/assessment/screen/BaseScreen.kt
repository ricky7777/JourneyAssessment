package com.journey.assessment.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.journey.assessment.R
import com.journey.assessment.widget.JourneyTopAppBar

enum class ScreenState {
    LOADING, NO_DATA_NO_INTERNET, SHOW_CONTENT, NO_CONTENT
}

/**
 * @author Ricky Chen
 * Base Screen, share use in PostScreen&CommentsDetailScreen
 */
@Composable
fun BaseScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBackPress: () -> Unit,
    showBackButton: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            JourneyTopAppBar(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onBackPress = onBackPress,
                showBackButton = showBackButton
            )
        },
        content = { innerPadding ->
            content(innerPadding)
        }
    )
}

@Composable
fun ScreenContent(
    screenState: ScreenState,
    content: @Composable () -> Unit,
    onReload: () -> Unit
) {
    when (screenState) {
        ScreenState.LOADING -> LoadingView()
        ScreenState.NO_DATA_NO_INTERNET -> NoInternetScreen(onReload = onReload)
        ScreenState.SHOW_CONTENT -> content()
        ScreenState.NO_CONTENT -> NoContentView()
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NoContentView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.no_content_available))
    }
}