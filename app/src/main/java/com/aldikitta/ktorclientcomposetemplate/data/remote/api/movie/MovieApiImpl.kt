package com.aldikitta.ktorclientcomposetemplate.data.remote.api.movie

import com.aldikitta.ktorclientcomposetemplate.data.model.MovieResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesApiImpl @Inject constructor(
    private val httpClient: HttpClient,
) : MoviesApi {
    override suspend fun getPopularMovies(
        page: Int,
        language: String,
        region: String
    ): MovieResponse {
        return httpClient.get("movie/popular") {
            parameter("page", page)
            parameter("language", language)
            parameter("region", region)
        }.body()
    }
}