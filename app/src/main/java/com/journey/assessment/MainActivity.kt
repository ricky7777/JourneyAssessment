package com.journey.assessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
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

object NavRoutes {
    const val POST = "post"
    const val COMMENTS = "comments"
}

@Composable
fun MyAppNavHost() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.POST) {
        composable(NavRoutes.POST) {
            PostScreen(onNavigateToComments = {
                navController.navigate(NavRoutes.COMMENTS)
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