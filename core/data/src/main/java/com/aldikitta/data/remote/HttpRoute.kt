package com.aldikitta.data.remote

// object class to declare route for easy accessibilty
object HttpRoute {

    // baseUrl for the project been assigned to a private variable
    private const val BASE_URL = "https://api.themoviedb.org/3/movie"
    // concatenation of the baseUrl with the endpoint
    const val LIST_MOVIE = "$BASE_URL/popular"
    // api_key been assigned a variable
    const val API_KEY = "59cd6896d8432f9c69aed9b86b9c2931"
    // language been assigned a variable
    const val LANGUAGE = "en-US"
}