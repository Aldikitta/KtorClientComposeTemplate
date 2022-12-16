package com.aldikitta.ktorclientcomposetemplate.data.remote.api.movie

import com.aldikitta.ktorclientcomposetemplate.data.model.ContentLanguage
import com.aldikitta.ktorclientcomposetemplate.data.model.MovieResponse

interface MoviesApi {
    suspend fun getPopularMovies(
        page: Int,
        language: String = ContentLanguage.default.languageCode,
        region: String = ContentLanguage.default.region
    ): MovieResponse
}