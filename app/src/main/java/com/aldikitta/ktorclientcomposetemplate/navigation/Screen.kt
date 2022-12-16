package com.aldikitta.ktorclientcomposetemplate.navigation

sealed class Screen(val route: String) {
    object RootScreen: Screen("root_screen")
    object RemoteScreen: Screen("remote_screen")
    object LocalScreen: Screen("local_screen")
}