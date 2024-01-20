package com.aldikitta.movie_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.MovieDetailRetrofit
import com.aldikitta.data.remote.Response
import com.aldikitta.domain.usecase.GetDeviceLanguageUseCase
import com.aldikitta.domain.usecase.movie_details.GetMovieDetailsUseCase
import com.aldikitta.movie_detail.navigation.MovieDetailArgs
import com.aldikitta.util.KreditPlusResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

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

    val uiState: StateFlow<MovieDetailUIState> = getMovieDetailsUseCase.movieDetail(
        movieId = movieDetailArgs.movieId,
        language = contentLanguage
    ).map {
        when (it) {
            is Response.Success -> {
                Log.d("MYTAG", "firstflow: ")
                MovieDetailUIState.Success(it.data)
            }

            is Response.Loading -> MovieDetailUIState.Loading
            is Response.Error -> MovieDetailUIState.Error(it.exception.toString())
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieDetailUIState.Loading
    )


    /* viewModelScope.launch from suspend function (not flow)
    * for single shot call, cannot be call in stateIn, once called,
    * it stop emitting a value, so only one time call
    * */
    fun movieDetailSuspend() {
        viewModelScope.launch {
            val movieDetail = getMovieDetailsUseCase.movieDetailsSuspend(
                movieId = movieDetailArgs.movieId,
                loadingState = {
                    Log.d("MYTAG", "movieDetailSuspend loading: ")
                }
            )
            when (movieDetail) {
                is KreditPlusResponse.Error -> {
                    Log.d("MYTAG", "movieDetailSuspend error: ")

                }

                is KreditPlusResponse.Success -> {
                    Log.d("MYTAG", "movieDetailSuspend success: ${movieDetail.data.title}")
                }

                else -> {}
            }
        }
    }

    /* stateIn always with flow, for stream data (this one is using operator (onStart, onCompletion, etc)
    * once call, it will always stream the latest data, it will stop if the subscriber is destroyed
    * */
    val movieDetailFlow =
        getMovieDetailsUseCase.movieDetailsFlow(movieId = movieDetailArgs.movieId).mapLatest {
            when (it) {
                is KreditPlusResponse.Loading -> {
                    Log.d("MYTAG", "movieDetailFlow loading: ")
                }

                is KreditPlusResponse.Success -> {
                    Log.d("MYTAG", "movieDetailFlow success: ${it.data.title}")

                }

                is KreditPlusResponse.Error -> {
                    Log.d("MYTAG", "movieDetailFlow error: ")

                }

                else -> {}
            }
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(1.seconds),
                KreditPlusResponse.Loading(status = false)
            )

    /* This one is flow also, similar to movieDetailFlow, but this time its using viewModelScope.launch
    * and once call, its always stream data, until the subscriber is destroyed
    * */
    fun movieDetailFlow() {
        viewModelScope.launch {
            getMovieDetailsUseCase.movieDetailsFlow(movieId = movieDetailArgs.movieId)
                .collectLatest {
                    when (it) {
                        is KreditPlusResponse.Loading -> {
                            Log.d("MYTAG", "movieDetailFlow in suspend loading: ")
                        }

                        is KreditPlusResponse.Success -> {
                            Log.d("MYTAG", "movieDetailFlow in suspend success: ${it.data.title}")

                        }

                        is KreditPlusResponse.Error -> {
                            Log.d("MYTAG", "movieDetailFlow in suspend error: ")

                        }

                        else -> {}
                    }
                }
        }
    }

    /* stateIn always with flow, for stream data (this one is using operator (tryCatch)
    * once call, it will always stream the latest data, it will stop if the subscriber is destroyed
    * */
    val movieDetailFlowTryCatch =
        getMovieDetailsUseCase.movieDetailsFlowWithTryCatch(movieId = movieDetailArgs.movieId)
            .mapLatest {
                when (it) {
                    is KreditPlusResponse.Loading -> {
                        Log.d("MYTAG", "movieDetailFlowTryCatch loading: ")
                    }

                    is KreditPlusResponse.Success -> {
                        Log.d("MYTAG", "movieDetailFlowTryCatch success: ${it.data.title}")

                    }

                    is KreditPlusResponse.Error -> {
                        Log.d("MYTAG", "movieDetailFlowTryCatch error: ")

                    }

                    else -> {}
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(1.seconds),
                KreditPlusResponse.Loading(status = false)
            )

    /* This one is flow also, similar to movieDetailFlowTryCatch, but this time its using viewModelScope.launch
    * and once call, its always stream data, until the subscriber is destroyed
    * */
    fun movieDetailFlowTryCatch() {
        viewModelScope.launch {
            getMovieDetailsUseCase.movieDetailsFlow(movieId = movieDetailArgs.movieId)
                .collectLatest {
                    when (it) {
                        is KreditPlusResponse.Loading -> {
                            Log.d("MYTAG", "movieDetailFlowTryCatch in suspend loading: ")
                        }

                        is KreditPlusResponse.Success -> {
                            Log.d("MYTAG", "movieDetailFlowTryCatch in suspend success: ${it.data.title}")

                        }

                        is KreditPlusResponse.Error -> {
                            Log.d("MYTAG", "movieDetailFlowTryCatch in suspend error: ")

                        }

                        else -> {}
                    }
                }
        }
    }
}


