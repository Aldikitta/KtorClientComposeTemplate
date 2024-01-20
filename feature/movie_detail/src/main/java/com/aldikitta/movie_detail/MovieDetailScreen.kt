package com.aldikitta.movie_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aldikitta.data.model.DetailPresentableMovie
import com.aldikitta.data.model.MovieDetail

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val movieDetailFlow by viewModel.movieDetailFlow.collectAsStateWithLifecycle()
//    val movieDetailFlowTryCatch by viewModel.movieDetailFlowTryCatch.collectAsStateWithLifecycle()

//    LaunchedEffect(Unit) {
//        viewModel.movieDetailSuspend()
//        viewModel.movieDetailFlow()
//        viewModel.movieDetailFlowTryCatch()
//    }

    MovieDetailResponseHandling(movieDetailUIState = uiState, onClick = {
        viewModel.movieDetailSuspend()
        viewModel.movieDetailFlow()
        viewModel.movieDetailFlowTryCatch()
    })
}

@Composable
fun MovieDetailResponseHandling(
    movieDetailUIState: MovieDetailUIState,
    onClick: () -> Unit
) {
    when (movieDetailUIState) {
        is MovieDetailUIState.Loading -> CircularProgressIndicator()
        is MovieDetailUIState.Success -> MovieDetailScreenContent(
            movieDetail = movieDetailUIState.movieDetail,
            onClick = onClick
        )

        is MovieDetailUIState.Error -> Error()
        is MovieDetailUIState.Nothing -> Nothing()
    }
}

@Composable
fun MovieDetailScreenContent(
    movieDetail: DetailPresentableMovie,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column() {
            Text(text = "This is detail")
            Text(text = movieDetail.title)
            Button(onClick = { onClick.invoke() }) {
                Text(text = "Trigger")
            }
        }
    }
}

@Composable
fun Error(
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Error")
    }
}

@Composable
fun Nothing(
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Nothing")
    }
}
