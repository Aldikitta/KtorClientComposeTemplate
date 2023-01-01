package com.aldikitta.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldikitta.data.local.AppDatabase
import com.aldikitta.data.local.model.MovieEntity
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.Movie
import com.aldikitta.data.model.MovieDetail
import com.aldikitta.data.paging.local.MovieResponsePagingMediator
import com.aldikitta.data.paging.remote.MovieResponsePagingDataSource
import com.aldikitta.data.remote.api.movie.MoviesApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val moviesApi: MoviesApi,
    private val appDatabase: AppDatabase
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

    @OptIn(ExperimentalPagingApi::class)
    override fun popularMoviesFromLocalCache(contentLanguage: ContentLanguage): Flow<PagingData<MovieEntity>> =
        Pager(
            PagingConfig(pageSize = 20),
            remoteMediator = MovieResponsePagingMediator(
                appDatabase = appDatabase,
                language = contentLanguage.languageCode,
                region = contentLanguage.region,
                moviesApi = moviesApi
            ),
            pagingSourceFactory = {
                appDatabase.moviesDao().getAllMovies(language = contentLanguage.languageCode)
            }
        ).flow.flowOn(defaultDispatcher)

    override fun movieDetails(movieId: Int, language: String): Flow<MovieDetail> = flow {
        while (true){
            val movieDetail = moviesApi.getMovieDetails(movieId, language)
            emit(movieDetail)
            delay(5000L)
        }
    }
}