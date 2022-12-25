package com.aldikitta.local_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.domain.usecase.GetDeviceLanguageUseCase
import com.aldikitta.domain.usecase.GetPopularMoviesFromLocalCacheUseCase
import com.aldikitta.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LocalViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val getPopularMoviesFromLocalCacheUseCase: GetPopularMoviesFromLocalCacheUseCase
) : ViewModel() {
    private val deviceLanguage: Flow<ContentLanguage> = getDeviceLanguageUseCase()

    private val localMovieUIState: StateFlow<LocalMovieUIState> =
        deviceLanguage.mapLatest { contentLanguage ->
            LocalMovieUIState(
                popular = getPopularMoviesFromLocalCacheUseCase(contentLanguage = contentLanguage).cachedIn(
                    viewModelScope
                )
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10), LocalMovieUIState.default)

    val uiState: StateFlow<LocalScreenUIState> = localMovieUIState.mapLatest { movieState ->
        LocalScreenUIState(
            localMovieUIState = movieState
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, LocalScreenUIState.default)

}