package com.aldikitta.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.Movie
import com.aldikitta.data.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(contentLanguage: ContentLanguage): Flow<PagingData<Movie>> {
        return moviesRepository.popularMovies(
            contentLanguage
        ).mapLatest { pagingMovie ->
            pagingMovie.map { movie ->
                movie
            }
        }
    }
}