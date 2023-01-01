package com.aldikitta.ktorclientcomposetemplate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aldikitta.ktorclientcomposetemplate.presentation.root_screen.RootScreen
import com.aldikitta.local_request.LocalScreen
import com.aldikitta.movie_detail.navigation.movieDetailScreen
import com.aldikitta.movie_detail.navigation.navigateToMovieDetail
import com.aldikitta.remote_request.RemoteScreen
import com.aldikitta.remote_request.navigation.remoteGraph

@Composable
fun Navigation(
//    navController: NavHostController,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.RootScreen.route
    ) {
        composable(route = Screen.RootScreen.route) {
            RootScreen(navController)
        }
        composable(route = Screen.LocalScreen.route) {
            LocalScreen(navController)
        }
        remoteGraph(
            navController,
            nestedGraphs = {
                movieDetailScreen()
            }
        )
    }
}