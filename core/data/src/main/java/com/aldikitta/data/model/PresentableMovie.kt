package com.aldikitta.data.model

import androidx.compose.runtime.Stable

@Stable
interface PresentableMovie {
    val id: Int
    val title: String
    val posterPath: String?
    val fullPosterPath: String
        get() = "https://image.tmdb.org/t/p/original/$posterPath"
}

@Stable
interface DetailPresentableMovie : PresentableMovie{
    val adult: Boolean?
    val overview: String?
    val backdropPath: String?
    val voteAverage: Float
    val voteCount: Int
}

