package com.projects.moviemanager.compose.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.compose.features.browse.ui.Browse
import com.projects.moviemanager.compose.features.details.DetailsScreen
import com.projects.moviemanager.compose.navigation.ScreenUI

class BrowseScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Browse(
            goToDetails = { contentId, mediaType ->
                navController.navigate(
                    DetailsScreen.routeWithArguments(contentId, mediaType.name)
                )
            }
        )
    }
}
