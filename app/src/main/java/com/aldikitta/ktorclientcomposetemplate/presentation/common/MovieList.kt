package com.aldikitta.ktorclientcomposetemplate.presentation.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aldikitta.ktorclientcomposetemplate.data.model.Movie

@Composable
fun MovieList(movies: List<Movie>) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(movies) { item ->
                MovieItem(item)
            }
        }
    }
}