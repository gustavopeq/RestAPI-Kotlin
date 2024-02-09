package com.projects.moviemanager.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projects.moviemanager.common.ui.screen.ErrorScreen
import com.projects.moviemanager.features.browse.BrowseScreen
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.features.home.HomeScreen
import com.projects.moviemanager.features.search.SearchScreen
import com.projects.moviemanager.features.watchlist.WatchlistScreen
import com.projects.moviemanager.navigation.screens.BrowseScreenUI
import com.projects.moviemanager.navigation.screens.DetailsScreenUI
import com.projects.moviemanager.navigation.screens.ErrorScreenUI
import com.projects.moviemanager.navigation.screens.HomeScreenUI
import com.projects.moviemanager.navigation.screens.SearchScreenUI
import com.projects.moviemanager.navigation.screens.WatchlistScreenUI

private val mainNavDestinations: Map<Screen, ScreenUI> = mapOf(
    HomeScreen to HomeScreenUI(),
    BrowseScreen to BrowseScreenUI(),
    WatchlistScreen to WatchlistScreenUI(),
    SearchScreen to SearchScreenUI(),
    DetailsScreen to DetailsScreenUI(),
    ErrorScreen to ErrorScreenUI()
)

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen.route(),
        enterTransition = {
            if (targetState.destination.route == SearchScreen.route()) {
                slideInVertically(
                    initialOffsetY = { -it }, // Start from above the screen
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            } else {
                if (initialState.destination.route == SearchScreen.route()) {
                    fadeIn(animationSpec = tween(300))
                } else {
                    EnterTransition.None
                }
            }
        },
        exitTransition = {
            if (initialState.destination.route == SearchScreen.route()) {
                slideOutVertically( // Start from above the screen
                    animationSpec = tween(300)
                )
            } else {
                ExitTransition.None
            }
        }
    ) {
        mainNavDestinations.forEach { (screen, screenUI) ->
            composable(screen.route(), screen.arguments) {
                screenUI.UI(
                    navController = navController,
                    navArguments = it.arguments
                )
            }
        }
    }
}
