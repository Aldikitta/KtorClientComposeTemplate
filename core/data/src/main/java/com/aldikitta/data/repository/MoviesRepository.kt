package com.aldikitta.data.repository

import androidx.paging.PagingData
import com.aldikitta.data.local.model.MovieEntity
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.Movie
import com.aldikitta.data.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun popularMovies(
        contentLanguage: ContentLanguage = ContentLanguage.default
    ): Flow<PagingData<Movie>>

    fun popularMoviesFromLocalCache(
        contentLanguage: ContentLanguage = ContentLanguage.default
    ): Flow<PagingData<MovieEntity>>

    fun movieDetails(
        movieId: Int,
        language: String = ContentLanguage.default.languageCode
    ): Flow<MovieDetail>
}