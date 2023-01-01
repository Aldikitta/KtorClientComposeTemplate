package com.aldikitta.movie_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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

    MovieDetailResponseHandling(movieDetailUIState = uiState)
}

@Composable
fun MovieDetailResponseHandling(
    movieDetailUIState: MovieDetailUIState
) {
    when (movieDetailUIState) {
        is MovieDetailUIState.Loading -> CircularProgressIndicator()
        is MovieDetailUIState.Success -> MovieDetailScreenContent(movieDetail = movieDetailUIState.movieDetail)
        is MovieDetailUIState.Error -> Error()
        is MovieDetailUIState.Nothing -> Nothing()
    }
}

@Composable
fun MovieDetailScreenContent(
    movieDetail: DetailPresentableMovie
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column() {
            Text(text = "This is detail")
            Text(text = movieDetail.title)
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
