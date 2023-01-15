package com.aldikitta.ktorclientcomposetemplate.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.aldikitta.ktorclientcomposetemplate.navigation.TopLevelDestination
import com.aldikitta.local_request.navigation.localRoute
import com.aldikitta.local_request.navigation.navigateToLocal
import com.aldikitta.remote_request.navigation.navigateToRemoteGraph
import com.aldikitta.remote_request.navigation.remoteRoute
import com.aldikitta.util.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberKtorAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): KtorAppState {
    return remember(navController, coroutineScope, windowSizeClass, networkMonitor){
        KtorAppState(navController, coroutineScope, windowSizeClass, networkMonitor)
    }
}

@Stable
class KtorAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            localRoute -> TopLevelDestination.LOCAL
            remoteRoute -> TopLevelDestination.REMOTE
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar


    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_0000),
            initialValue = false
        )

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination){
        trace("Navigation: ${topLevelDestination.name}"){
            val topLevelNavOptions = navOptions{
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination){
                TopLevelDestination.REMOTE -> navController.navigateToRemoteGraph(topLevelNavOptions)
                TopLevelDestination.LOCAL -> navController.navigateToLocal(topLevelNavOptions)
            }
        }
    }

    fun onBackClick(){
        navController.popBackStack()
    }
}

///**
// * Stores information about navigation events to be used with JankStats
// */
//TODO CHECK THIS JANK
//@Composable
//private fun NavigationTrackingSideEffect(navController: NavHostController) {
//    TrackDisposableJank(navController) { metricsHolder ->
//        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
//            metricsHolder.state?.putState("Navigation", destination.route.toString())
//        }
//
//        navController.addOnDestinationChangedListener(listener)
//
//        onDispose {
//            navController.removeOnDestinationChangedListener(listener)
//        }
//    }
//}