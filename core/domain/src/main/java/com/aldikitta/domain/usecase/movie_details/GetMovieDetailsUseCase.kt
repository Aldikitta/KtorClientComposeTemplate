package com.aldikitta.domain.usecase.movie_details

import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.MovieDetail
import com.aldikitta.data.model.MovieDetailRetrofit
import com.aldikitta.data.remote.Response
import com.aldikitta.util.KreditPlusResponse
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {
    fun movieDetail(
        movieId: Int,
        language: ContentLanguage
    ): Flow<Response<MovieDetail>>

    suspend fun movieDetailsSuspend(
        movieId: Int,
        loadingState: (Boolean) -> Unit
    ): KreditPlusResponse<MovieDetailRetrofit.MovieDetail>

    fun movieDetailsFlow(
        movieId: Int,
    ): Flow<KreditPlusResponse<MovieDetailRetrofit.MovieDetail>>

    fun movieDetailsFlowWithTryCatch(
        movieId: Int,
    ): Flow<KreditPlusResponse<MovieDetailRetrofit.MovieDetail>>

}