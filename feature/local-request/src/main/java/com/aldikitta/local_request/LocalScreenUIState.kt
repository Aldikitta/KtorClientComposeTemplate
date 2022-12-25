package com.aldikitta.local_request

import androidx.paging.PagingData
import com.aldikitta.data.local.model.MovieEntity
import com.aldikitta.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class LocalScreenUIState(
    val localMovieUIState: LocalMovieUIState
) {
    companion object {
        val default: LocalScreenUIState = LocalScreenUIState(
            localMovieUIState = LocalMovieUIState.default
        )
    }
}

data class LocalMovieUIState(
    val popular: Flow<PagingData<MovieEntity>>
) {
    companion object {
        val default: LocalMovieUIState = LocalMovieUIState(
            popular = emptyFlow()
        )
    }
}