package com.journey.assessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.journey.assessment.screen.CommentsDetailScreen
import com.journey.assessment.screen.PostScreen
import com.journey.assessment.ui.theme.JourneyAssessmentTheme

/**
 * @author Ricky Chen
 * Main entry point
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JourneyAssessmentTheme {
                MyAppNavHost()
            }
        }
    }
}

@Composable
fun MyAppNavHost() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "post") {
        composable("post") {
            PostScreen(onNavigateToComments = {
                navController.navigate("comments")
            }, onBackPress = {
                navController.popBackStack()
            })
        }

        composable("comments") {
            CommentsDetailScreen(onBackPress = {
                navController.popBackStack()
            })
        }
    }
}