package com.aldikitta.movie_detail

sealed interface MovieDetailUIEvent {
    data class SetSelectedMovie(val movieId: Int) : MovieDetailUIEvent
    object CloseDetailOnlyOpen : MovieDetailUIEvent
}