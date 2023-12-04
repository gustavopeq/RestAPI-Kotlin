package com.projects.moviemanager.compose.features.details

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.projects.moviemanager.compose.navigation.Screen

object DetailsScreen : Screen {
    private const val DETAILS_ROUTE = "details"
    const val ARG_ID = "contentId"
    const val ARG_MEDIA_TYPE = "mediaType"
    private const val FULL_DETAILS_ROUTE =
        "$DETAILS_ROUTE/{$ARG_ID}?$ARG_MEDIA_TYPE={$ARG_MEDIA_TYPE}"
    override fun route(): String = FULL_DETAILS_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ARG_ID) {
                type = NavType.IntType
            }
        )

    fun routeWithArguments(
        contentId: Int,
        mediaType: String
    ): String {
        return buildString {
            append(DETAILS_ROUTE)
            append("/$contentId?")
            append("$ARG_MEDIA_TYPE=$mediaType")
        }
    }
}
