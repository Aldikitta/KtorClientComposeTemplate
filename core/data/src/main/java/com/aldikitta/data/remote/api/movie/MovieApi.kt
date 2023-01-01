package com.aldikitta.data.remote.api.movie

import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.MovieDetail
import com.aldikitta.data.model.MovieResponse

interface MoviesApi {
    suspend fun getPopularMovies(
        page: Int,
        language: String = ContentLanguage.default.languageCode,
        region: String = ContentLanguage.default.region
    ): MovieResponse

    suspend fun getMovieDetails(
        movieId: Int,
        language: String = ContentLanguage.default.languageCode
    ): MovieDetail
}