package com.aldikitta.local_request.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aldikitta.local_request.LocalScreen

const val localRoute = "local_route"

fun NavController.navigateToLocal(navOptions: NavOptions? = null) {
    this.navigate(localRoute, navOptions)
}

fun NavGraphBuilder.localScreen(
    navController: NavController,
) {
    composable(route = localRoute){
        LocalScreen(navController = navController)
    }
}