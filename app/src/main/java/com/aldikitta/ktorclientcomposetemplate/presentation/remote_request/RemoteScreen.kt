package com.aldikitta.ktorclientcomposetemplate.presentation.remote_request

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.aldikitta.ktorclientcomposetemplate.R
import com.aldikitta.ktorclientcomposetemplate.data.model.Movie
import com.aldikitta.ktorclientcomposetemplate.presentation.common.MovplayPresentableGridSection
import com.aldikitta.ktorclientcomposetemplate.ui.theme.spacing

@Composable
fun RemoteScreen(
    navController: NavController,
    viewModel: RemoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val onBackClicked: () -> Unit = { navController.navigateUp() }

    RemoteScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteScreenContent(
    uiState: RemoteScreenUIState,
    onBackClicked: () -> Unit,
) {
    val popularMovies = uiState.remoteMovieUIState.popular.collectAsLazyPagingItems()

    val gridState = rememberLazyGridState()

    Box(modifier = Modifier.fillMaxSize()){
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
            MovplayPresentableGridSection(
                modifier = Modifier.fillMaxSize(),
                state = popularMovies,
                gridState = gridState,
                contentPadding = PaddingValues(
                    top = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.large
                ),
            )
        }
    }

}