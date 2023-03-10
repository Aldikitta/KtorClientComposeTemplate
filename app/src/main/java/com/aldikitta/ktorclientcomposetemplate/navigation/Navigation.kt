package com.aldikitta.ktorclientcomposetemplate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aldikitta.ktorclientcomposetemplate.presentation.root_screen.RootScreen
import com.aldikitta.local_request.LocalScreen
import com.aldikitta.remote_request.RemoteScreen

@Composable
fun Navigation() {
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
        composable(route = Screen.RemoteScreen.route) {
            RemoteScreen(navController)
        }
    }
}