package com.aldikitta.remote_request.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.aldikitta.remote_request.RemoteScreen

private const val remoteGraphRoutePattern = "remote_screen_graph"
const val remoteRoute = "remote_route"

fun NavController.navigateToRemoteGraph(navOptions: NavOptions? = null) {
    this.navigate(remoteGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.remoteGraph(
    navController: NavController,
    navigateToMovieDetail: (Int) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = remoteGraphRoutePattern,
        startDestination = remoteRoute
    ) {
        composable(route = remoteRoute){
            RemoteScreen(
                navController = navController,
                onMovieClicked = navigateToMovieDetail
            )
        }
        nestedGraphs()
    }
}