package com.journey.assessment

import android.net.Uri
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
    const val POST_ID = "postId"
    const val POST_TITLE = "postTitle"
}

@Composable
fun MyAppNavHost() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.POST) {
        composable(NavRoutes.POST) {
            PostScreen(onNavigateToComments = { postId, postTitle ->
                val encodedTitle = Uri.encode(postTitle)
                navController.navigate("${NavRoutes.COMMENTS}/$postId/$encodedTitle")
            }, onBackPress = {
                navController.popBackStack()
            })
        }

        composable(
            route = "${NavRoutes.COMMENTS}/{${NavRoutes.POST_ID}}/{${NavRoutes.POST_TITLE}}",
            arguments = listOf(
                navArgument(NavRoutes.POST_ID) { type = NavType.IntType },
                navArgument(NavRoutes.POST_TITLE) { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.run {
                val postId = getInt(NavRoutes.POST_ID)
                val postTitle = getString(NavRoutes.POST_TITLE)
                postTitle?.run {
                    CommentsDetailScreen(postId = postId, postTitle = this, onBackPress = {
                        navController.popBackStack()
                    })
                }
            }
        }
    }
}