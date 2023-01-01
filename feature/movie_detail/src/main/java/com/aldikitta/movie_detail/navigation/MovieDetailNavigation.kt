package com.aldikitta.movie_detail.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aldikitta.movie_detail.MovieDetailScreen

internal const val movieIdArg = "movieId"

internal class MovieDetailArgs(val movieId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[movieIdArg]) as Int)
}

internal class DetailArgs(savedStateHandle: SavedStateHandle) {
    var rateId: Int? = savedStateHandle.get<Int>(movieIdArg)
}

fun NavController.navigateToMovieDetail(movieId: Int) {
    val encodedId = Uri.encode(movieId.toString())
    this.navigate("movie_detail_route/$encodedId")
}

fun NavGraphBuilder.movieDetailScreen(
) {
    composable(
        route = "movie_detail_route/{$movieIdArg}",
        arguments = listOf(
            navArgument(movieIdArg) {
                type = NavType.IntType
            }
        )
    ) {
        MovieDetailScreen()
    }
}