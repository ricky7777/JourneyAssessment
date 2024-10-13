package com.journey.assessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.journey.assessment.screen.CommentsDetailScreen
import com.journey.assessment.screen.PostScreen
import com.journey.assessment.ui.theme.JourneyAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Ricky Chen
 * Main entry point
 */
@AndroidEntryPoint
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
    const val POST_ID = "posstId"
}

@Composable
fun MyAppNavHost() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.POST) {
        composable(NavRoutes.POST) {
            PostScreen(onNavigateToComments = { postId ->
                navController.navigate("${NavRoutes.COMMENTS}/$postId")
            }, onBackPress = {
                navController.popBackStack()
            })
        }

        composable(
            route = "${NavRoutes.COMMENTS}/{${NavRoutes.POST_ID}}",
            arguments = listOf(navArgument(NavRoutes.POST_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt(NavRoutes.POST_ID)
            CommentsDetailScreen(postId = postId, onBackPress = {
                navController.popBackStack()
            })
        }
    }
}