package com.aldikitta.data.paging.local

import androidx.paging.*
import androidx.room.withTransaction
import com.aldikitta.data.local.AppDatabase
import com.aldikitta.data.local.model.MovieEntity
import com.aldikitta.data.local.model.MoviesRemoteKeys
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.remote.api.movie.MoviesApi
import io.ktor.client.plugins.*
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MovieResponsePagingMediator(
    private val language: String = ContentLanguage.default.languageCode,
    private val region: String = ContentLanguage.default.region,
    private val moviesApi: MoviesApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, MovieEntity>() {
    private val movieCacheDao = appDatabase.moviesDao()
    private val remoteKeysDao = appDatabase.moviesRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val remoteKey = appDatabase.withTransaction {
            remoteKeysDao.getRemoteKey(
                language = language
            )
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeOut = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if ((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKey = appDatabase.withTransaction {
                        remoteKeysDao.getRemoteKey(
                            language = language
                        )
                    } ?: return MediatorResult.Success(true)

                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.nextPage
                }
            }

            val result = moviesApi.getPopularMovies(
                page = page,
                language = language,
                region = region
            )

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieCacheDao.deleteMoviesOfType(
                        language = language
                    )
                    remoteKeysDao.deleteRemoteKeysOfType(
                        language = language
                    )
                }

                val nextPage = if (result.results.isNotEmpty()) {
                    page + 1
                } else null

                val movieEntities = result.results.map { movie ->
                    MovieEntity(
                        id = movie.id,
                        title = movie.title,
                        originalTitle = movie.originalTitle,
                        overview = movie.overview,
                        language = language,
                        posterPath = movie.posterPath
                    )
                }

                remoteKeysDao.insertKey(
                    MoviesRemoteKeys(
                        language = language,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                )
                movieCacheDao.addMovies(movieEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = result.results.isEmpty()
            )
        } catch (e: IOException) {
            print("Error: ${e.message}")
            MediatorResult.Error(e)
        } catch (e: Exception) {
            println("${e.message}")
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: RedirectResponseException) {
            print("Error: ${e.response.status.description}")
            MediatorResult.Error(e)
            // handling errors from the server
        } catch (e: ServerResponseException) {
            print("Error: ${e.response.status.description}")
            MediatorResult.Error(e)
            // handling errors from the server
        } catch (e: ClientRequestException) {
            print("Error: ${e.response.status.description}")
            MediatorResult.Error(e)
            // handling errors from the server
        }
    }
}