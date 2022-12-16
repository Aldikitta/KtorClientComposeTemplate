package com.aldikitta.ktorclientcomposetemplate.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldikitta.ktorclientcomposetemplate.data.model.ContentLanguage
import com.aldikitta.ktorclientcomposetemplate.data.model.Movie
import com.aldikitta.ktorclientcomposetemplate.data.paging.MovieResponsePagingDataSource
import com.aldikitta.ktorclientcomposetemplate.data.remote.api.movie.MoviesApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val moviesApi: MoviesApi,
) : MoviesRepository {
    override fun popularMovies(contentLanguage: ContentLanguage): Flow<PagingData<Movie>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        MovieResponsePagingDataSource(
            moviesApi = moviesApi,
            language = contentLanguage.languageCode,
            region = contentLanguage.region
        )
    }.flow.flowOn(defaultDispatcher)
}