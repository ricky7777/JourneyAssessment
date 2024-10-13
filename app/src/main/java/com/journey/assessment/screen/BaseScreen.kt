package com.journey.assessment.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
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
    placeHolderText: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            JourneyTopAppBar(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onBackPress = onBackPress,
                showBackButton = showBackButton,
                placeHolderText
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                content(innerPadding)
            }
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

@Composable
fun addTextItemWithColor(title: Int, message: String, color: Color) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = color)) {
                append(stringResource(id = title))
            }
            append(message)
        }
    )
}