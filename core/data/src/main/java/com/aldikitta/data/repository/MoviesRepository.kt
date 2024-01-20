package com.aldikitta.data.repository

import androidx.paging.PagingData
import com.aldikitta.data.local.model.MovieEntity
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.Movie
import com.aldikitta.data.model.MovieDetail
import com.aldikitta.data.model.MovieDetailRetrofit
import com.aldikitta.data.remote.Response
import com.aldikitta.util.KreditPlusResponse
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

    // Play With Flow and Suspend Function
    suspend fun movieDetailsSuspend(
        movieId: Int,
        loadingState: (Boolean) -> Unit
    ): KreditPlusResponse<MovieDetailRetrofit.MovieDetail>

    fun movieDetailsFlow(
        movieId: Int,
    ): Flow<MovieDetailRetrofit.MovieDetail>

    fun movieDetailsFlowWithTryCatch(
        movieId: Int,
    ): Flow<KreditPlusResponse<MovieDetailRetrofit.MovieDetail>>
}