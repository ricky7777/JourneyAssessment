package com.journey.assessment.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import com.journey.assessment.R

/**
 * @author Ricky Chen
 * Screens share use this top app bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyTopAppBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBackPress: () -> Unit,
    showBackButton: Boolean = true
) {

    TopAppBar(
        title = {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(text = stringResource(R.string.search_placeholder))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                )
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackPress,
                modifier = Modifier.graphicsLayer(
                    alpha = if (showBackButton) 1f else 0f
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.text_back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        )
    )
}