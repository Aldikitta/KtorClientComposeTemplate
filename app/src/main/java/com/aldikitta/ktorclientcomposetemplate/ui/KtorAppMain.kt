package com.aldikitta.ktorclientcomposetemplate.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.aldikitta.ktorclientcomposetemplate.R
import com.aldikitta.ktorclientcomposetemplate.navigation.Navigation
import com.aldikitta.ktorclientcomposetemplate.navigation.TopLevelDestination
import com.aldikitta.util.NetworkMonitor

@OptIn(
    ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class
)
@Composable
fun KtorAppMain(
    networkMonitor: NetworkMonitor,
    windowSizeClass: WindowSizeClass,
    appState: KtorAppState = rememberKtorAppState(
        networkMonitor = networkMonitor,
        windowSizeClass = windowSizeClass
    )
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val notConnectedMessage = stringResource(id = R.string.not_connected)
    LaunchedEffect(isOffline) {
        if (isOffline) snackbarHostState.showSnackbar(
            message = notConnectedMessage,
            duration = SnackbarDuration.Indefinite
        )
    }

    Scaffold(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                KtorBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.testTag("KtorBottomBar")
                )
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .consumeWindowInsets(it)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            if (appState.shouldShowNavRail) {
                KtorNavRail(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier
                        .testTag("NiaNavRail")
                        .safeDrawingPadding()
                )
            }
            Navigation(navController = appState.navController)
        }

    }
}

@Composable
private fun KtorNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier
    ) {
        destinations.forEach {
            val selected = currentDestination.isTopLevelDestinationInHierarchy(it)
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(it) },
                icon = {
                    val icon = if (selected) {
                        it.selectedIcon
                    } else {
                        it.unselectedIcon
                    }
                    Icon(imageVector = icon, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(it.iconTextId))
                }
            )
        }
    }
}

@Composable
private fun KtorBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = Modifier
    ) {
        destinations.forEach {
            val selected = currentDestination.isTopLevelDestinationInHierarchy(it)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(it) },
                icon = {
                    val icon = if (selected) {
                        it.selectedIcon
                    } else {
                        it.unselectedIcon
                    }
                    Icon(imageVector = icon, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(it.iconTextId))
                }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false