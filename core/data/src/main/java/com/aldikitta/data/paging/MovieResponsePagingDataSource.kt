package com.aldikitta.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.Movie
import com.aldikitta.data.remote.api.movie.MoviesApi
import io.ktor.client.plugins.*
import java.io.IOException

class MovieResponsePagingDataSource(
    private val language: String = ContentLanguage.default.languageCode,
    private val region: String = ContentLanguage.default.region,
    private val moviesApi: MoviesApi
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val movieResponse = moviesApi.getPopularMovies(nextPage, language, region)

            val currentPage = movieResponse.page
            val totalPages = movieResponse.totalPages

            LoadResult.Page(
                data = movieResponse.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (currentPage + 1 > totalPages) null else currentPage + 1
            )
        } catch (e: IOException) {
            print("Error: ${e.message}")
            LoadResult.Error(e)
        } catch (e: Exception) {
            println("${e.message}")
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: RedirectResponseException) {
            print("Error: ${e.response.status.description}")
            LoadResult.Error(e)
            // handling errors from the server
        } catch (e: ServerResponseException) {
            print("Error: ${e.response.status.description}")
            LoadResult.Error(e)
            // handling errors from the server
        } catch (e: ClientRequestException) {
            print("Error: ${e.response.status.description}")
            LoadResult.Error(e)
            // handling errors from the server
        }
    }
}