package com.aldikitta.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.remote.Response
import com.aldikitta.domain.usecase.GetDeviceLanguageUseCase
import com.aldikitta.domain.usecase.movie_details.GetMovieDetailsUseCase
import com.aldikitta.movie_detail.navigation.MovieDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val movieDetailArgs: MovieDetailArgs = MovieDetailArgs(savedStateHandle)
    private val deviceLanguage: Flow<ContentLanguage> = getDeviceLanguageUseCase()

    private val contentLanguage: ContentLanguage = ContentLanguage.default

//    val uiState: StateFlow<MovieDetailUIState> = deviceLanguage.mapLatest { contentLanguage ->
//        getMovieDetailsUseCase(
//            movieId = movieDetailArgs.movieId,
//            language = contentLanguage
//        ).map {
//            when (it) {
//                is Response.Success -> MovieDetailUIState.Success(it.data)
//                is Response.Loading -> MovieDetailUIState.Loading
//                is Response.Error -> MovieDetailUIState.Error(it.exception.toString())
//            }
//        }
//    }.map {
//        MovieDetailUIState.Nothing
//    }.stateIn(
//        viewModelScope,
//        started = SharingStarted.WhileSubscribed(10),
//        initialValue = MovieDetailUIState.Nothing
//    )

    val uiState: StateFlow<MovieDetailUIState> = getMovieDetailsUseCase(
        movieId = movieDetailArgs.movieId,
        language = contentLanguage
    ).map {
        when (it) {
            is Response.Success -> MovieDetailUIState.Success(it.data)
            is Response.Loading -> MovieDetailUIState.Loading
            is Response.Error -> MovieDetailUIState.Error(it.exception.toString())
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieDetailUIState.Loading
    )
}


