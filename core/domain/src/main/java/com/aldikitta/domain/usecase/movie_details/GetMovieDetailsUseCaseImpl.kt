package com.aldikitta.domain.usecase.movie_details

import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.MovieDetail
import com.aldikitta.data.remote.Response
import com.aldikitta.data.remote.asResponse
import com.aldikitta.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovieDetailsUseCase {
    override fun invoke(
        movieId: Int,
        language: ContentLanguage
    ): Flow<Response<MovieDetail>> {
        return moviesRepository.movieDetails(
            movieId = movieId,
            language = language.languageCode
        ).asResponse()
    }
}