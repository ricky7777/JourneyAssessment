package com.journey.assessment.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    showBackButton: Boolean = true,
    placeHolderText: String
) {

    TopAppBar(
        title = {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(text = placeHolderText, fontSize = 16.sp)
                },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .height(52.dp),
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
                modifier = Modifier.offset(y = 5.dp)
            ) {
                var imageVector = Icons.Default.ArrowBack
                if (!showBackButton) {
                    imageVector = Icons.Default.Home
                }
                Icon(
                    imageVector = imageVector,
                    contentDescription = stringResource(R.string.text_back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        )
    )
}