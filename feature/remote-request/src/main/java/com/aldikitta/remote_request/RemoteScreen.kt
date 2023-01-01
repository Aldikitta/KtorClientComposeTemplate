package com.aldikitta.remote_request

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.aldikitta.remote_request.components.RemotePresentableSection
import com.aldikitta.ui.theme.spacing
import com.aldikitta.ui.util.isAnyRefreshing
import com.aldikitta.ui.util.refreshAll

@Composable
fun RemoteScreen(
    navController: NavController,
    onMovieClicked: (Int) -> Unit,
    viewModel: RemoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val onBackClicked: () -> Unit = { navController.navigateUp() }

    RemoteScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onMovieClicked = onMovieClicked
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun RemoteScreenContent(
    uiState: RemoteScreenUIState,
    onBackClicked: () -> Unit,
    onMovieClicked: (Int) -> Unit,
) {
    val popularMovies = uiState.remoteMovieUIState.popular.collectAsLazyPagingItems()

    val gridState = rememberLazyGridState()

    val isRefreshing by derivedStateOf {
        listOf(
            popularMovies
        ).isAnyRefreshing()
    }

    val swipeRefreshState = rememberPullRefreshState(
        isRefreshing,
        onRefresh = {
            listOf(
                popularMovies
            ).refreshAll()
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = swipeRefreshState)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.popular))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "go back",
                        )
                    }
                }
            )
            if (!isRefreshing) {
                RemotePresentableSection(
                    modifier = Modifier.fillMaxSize(),
                    state = popularMovies,
                    gridState = gridState,
                    onPresentableClick = onMovieClicked,
                    contentPadding = PaddingValues(
                        top = MaterialTheme.spacing.medium,
                        start = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.large
                    ),
                )
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = swipeRefreshState,
            Modifier.align(Alignment.TopCenter),
            scale = true
        )
    }
}