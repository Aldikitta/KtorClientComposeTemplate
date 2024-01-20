package com.aldikitta.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldikitta.data.local.AppDatabase
import com.aldikitta.data.local.model.MovieEntity
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.Movie
import com.aldikitta.data.model.MovieDetail
import com.aldikitta.data.model.MovieDetailRetrofit
import com.aldikitta.data.paging.local.MovieResponsePagingMediator
import com.aldikitta.data.paging.remote.MovieResponsePagingDataSource
import com.aldikitta.data.remote.Response
import com.aldikitta.data.remote.api.movie.ApiService
import com.aldikitta.data.remote.api.movie.MoviesApi
import com.aldikitta.util.AppException
import com.aldikitta.util.KreditPlusResponse
import com.aldikitta.util.asSuspendKreditPlusResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val moviesApi: MoviesApi,
    private val appDatabase: AppDatabase,
    private val apiService: ApiService
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
        while (true) {
            val movieDetail = moviesApi.getMovieDetails(movieId, language)
            emit(movieDetail)
            delay(5000L)
        }
    }

    override suspend fun movieDetailsSuspend(
        movieId: Int,
        loadingState: (Boolean) -> Unit
    ): KreditPlusResponse<MovieDetailRetrofit.MovieDetail> {
        return asSuspendKreditPlusResponse(
            apiCall = {
                apiService.movieDetail(
                    movieId
                )
            },
            loadingState = loadingState
        )
    }

    override fun movieDetailsFlow(
        movieId: Int,
    ): Flow<MovieDetailRetrofit.MovieDetail> = flow {
        while (true) {
            val movie = apiService.movieDetail(movieId)
            emit(movie)
            delay(1000L)
        }
    }

    override fun movieDetailsFlowWithTryCatch(movieId: Int): Flow<KreditPlusResponse<MovieDetailRetrofit.MovieDetail>> = flow {
        try {
            emit(KreditPlusResponse.Loading(status = true))
            delay(1.seconds)
            val movie = apiService.movieDetail(movieId)
            emit(KreditPlusResponse.Success(movie))
        } catch (e: Exception) {
            emit(KreditPlusResponse.Error(exception = AppException(cause = e.cause)))
        }
    }
}