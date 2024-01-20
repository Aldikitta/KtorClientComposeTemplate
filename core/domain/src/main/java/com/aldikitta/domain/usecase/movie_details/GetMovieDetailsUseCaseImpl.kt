package com.aldikitta.domain.usecase.movie_details

import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.MovieDetail
import com.aldikitta.data.model.MovieDetailRetrofit
import com.aldikitta.data.remote.Response
import com.aldikitta.data.remote.asResponse
import com.aldikitta.data.repository.MoviesRepository
import com.aldikitta.util.KreditPlusResponse
import com.aldikitta.util.asFlowKreditPlusResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovieDetailsUseCase {

    override fun movieDetail(movieId: Int, language: ContentLanguage): Flow<Response<MovieDetail>> {
        return moviesRepository.movieDetails(
            movieId = movieId,
            language = language.languageCode
        ).asResponse()
    }

    override suspend fun movieDetailsSuspend(
        movieId: Int,
        loadingState: (Boolean) -> Unit
    ): KreditPlusResponse<MovieDetailRetrofit.MovieDetail> {
        return moviesRepository.movieDetailsSuspend(movieId, loadingState)
    }

    override fun movieDetailsFlow(
        movieId: Int,
    ): Flow<KreditPlusResponse<MovieDetailRetrofit.MovieDetail>> {
        return moviesRepository.movieDetailsFlow(movieId).asFlowKreditPlusResponse()
    }

    override fun movieDetailsFlowWithTryCatch(movieId: Int): Flow<KreditPlusResponse<MovieDetailRetrofit.MovieDetail>> {
        return moviesRepository.movieDetailsFlowWithTryCatch(movieId)    }
}