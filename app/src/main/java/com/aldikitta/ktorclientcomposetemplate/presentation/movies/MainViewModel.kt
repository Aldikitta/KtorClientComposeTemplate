package com.aldikitta.ktorclientcomposetemplate.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldikitta.ktorclientcomposetemplate.data.model.MovieResponse
import com.aldikitta.ktorclientcomposetemplate.data.remote.Resource
import com.aldikitta.ktorclientcomposetemplate.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class MainViewModel @Inject constructor(
//    private val repository: MoviesRepository
//) : ViewModel() {
//
////    private val _movies = MutableStateFlow<Resource<List<Movie>>?>(null)
////    val movies: StateFlow<Resource<List<Movie>>?> = _movies
////
////    init {
////        viewModelScope.launch {
////            _movies.value = Resource.Loading
////            _movies.value = repository.getPopularMovies()
////        }
////    }
//
//    private val _movies = MutableStateFlow<Resource<MovieResponse>>(Resource.Loading)
//    val movies: StateFlow<Resource<MovieResponse>> = _movies
//
//    init {
//        viewModelScope.launch {
//            _movies.value = Resource.Loading
//            _movies.value = repository.getPopularMovies(page = 1)
//        }
//    }
//}