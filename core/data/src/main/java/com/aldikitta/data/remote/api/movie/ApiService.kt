package com.aldikitta.data.remote.api.movie

import com.aldikitta.data.model.MovieDetailRetrofit
import retrofit2.http.GET
import retrofit2.http.Path

const val MOVIE_DETAIL = "movie/{movieId}?api_key=59cd6896d8432f9c69aed9b86b9c2931&language=en-US"
interface ApiService {
    @GET(MOVIE_DETAIL)
    suspend fun movieDetail(@Path("movieId") movieId: Int): MovieDetailRetrofit.MovieDetail
}