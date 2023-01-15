package com.aldikitta.ktorclientcomposetemplate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aldikitta.ktorclientcomposetemplate.presentation.root_screen.RootScreen
import com.aldikitta.local_request.LocalScreen
import com.aldikitta.local_request.navigation.localRoute
import com.aldikitta.local_request.navigation.localScreen
import com.aldikitta.movie_detail.navigation.movieDetailScreen
import com.aldikitta.movie_detail.navigation.navigateToMovieDetail
import com.aldikitta.remote_request.RemoteScreen
import com.aldikitta.remote_request.navigation.remoteGraph
import com.aldikitta.remote_request.navigation.remoteRoute

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String = localRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
//        composable(route = Screen.RootScreen.route) {
//            RootScreen(navController)
//        }
//        composable(route = Screen.LocalScreen.route) {
//            LocalScreen(navController)
//        }
        localScreen(navController)
        remoteGraph(
            navController,
            nestedGraphs = {
                movieDetailScreen()
            }
        )
    }
}