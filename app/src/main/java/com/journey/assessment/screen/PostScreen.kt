package com.journey.assessment.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.journey.assessment.widget.JourneyTopAppBar


/**
 * @author Ricky Chen
 * Post main screen
 * responsible show post list and navigate to comments detail screen
 */
@Composable
fun PostScreen(onNavigateToComments: () -> Unit, onBackPress: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            JourneyTopAppBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onBackPress = onBackPress,
                false
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Post Fragment (Compose)")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateToComments) {
                    Text(text = "Go to Comments")
                }
            }
        }
    )
}