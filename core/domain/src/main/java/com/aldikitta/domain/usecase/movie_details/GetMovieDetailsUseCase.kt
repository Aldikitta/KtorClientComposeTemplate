package com.aldikitta.domain.usecase.movie_details

import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.MovieDetail
import com.aldikitta.data.remote.Response
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {
    operator fun invoke(
        movieId: Int,
        language: ContentLanguage
    ): Flow<Response<MovieDetail>>
}