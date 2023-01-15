package com.aldikitta.ktorclientcomposetemplate.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.aldikitta.ktorclientcomposetemplate.R
import com.aldikitta.remote_request.navigation.remoteRoute

//data class AdaptiveTopLevelDestination(
//    val route: String,
//    val selectedIcon: ImageVector,
//    val unSelectedIcon: ImageVector,
//    val iconTextId: Int,
//)
//
//class NavigationActionEvent(
//    private val navController: NavHostController
//) {
//    fun navigateTo(destination: AdaptiveTopLevelDestination) {
//        navController.navigate(destination.route) {
//            // Pop up to the start destination of the graph to
//            // avoid building up a large stack of destinations
//            // on the back stack as users select items
//            popUpTo(navController.graph.findStartDestination().id) {
//                saveState = true
//            }
//            // Avoid multiple copies of the same destination when
//            // reselecting the same item
//            launchSingleTop = true
//            // Restore state when reselecting a previously selected item
//            restoreState = true
//        }
//    }
//}
//
//val TOP_LEVEL_DESTINATIONS = listOf(
//    AdaptiveTopLevelDestination(
//        route = remoteRoute,
//        selectedIcon = Icons.Default.Inbox,
//        unSelectedIcon = Icons.Default.Inbox,
//        iconTextId = R.string.local,
//    ),
//    AdaptiveTopLevelDestination(
//        route = remoteRoute,
//        selectedIcon = Icons.Default.Article,
//        unSelectedIcon = Icons.Default.Article,
//        iconTextId = R.string.remote,
//    ),
//)

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    LOCAL(
        selectedIcon = Icons.Default.Article,
        unselectedIcon = Icons.Default.Article,
        iconTextId = R.string.local,
        titleTextId = R.string.local
    ),
    REMOTE(
        selectedIcon = Icons.Default.Inbox,
        unselectedIcon = Icons.Default.Inbox,
        iconTextId = R.string.remote,
        titleTextId = R.string.remote
    ),
}