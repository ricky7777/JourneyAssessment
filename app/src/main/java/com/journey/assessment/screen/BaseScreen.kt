package com.journey.assessment.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.journey.assessment.widget.JourneyTopAppBar

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