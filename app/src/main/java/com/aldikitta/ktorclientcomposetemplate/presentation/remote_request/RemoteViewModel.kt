package com.aldikitta.ktorclientcomposetemplate.presentation.remote_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aldikitta.ktorclientcomposetemplate.data.model.ContentLanguage
import com.aldikitta.ktorclientcomposetemplate.domain.usecase.GetDeviceLanguageUseCase
import com.aldikitta.ktorclientcomposetemplate.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RemoteViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val deviceLanguage: Flow<ContentLanguage> = getDeviceLanguageUseCase()

    private val remoteMovieUIState: StateFlow<RemoteMovieUIState> =
        deviceLanguage.mapLatest { contentLanguage ->
            RemoteMovieUIState(
                popular = getPopularMoviesUseCase(contentLanguage = contentLanguage).cachedIn(
                    viewModelScope
                )
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10), RemoteMovieUIState.default)

    val uiState: StateFlow<RemoteScreenUIState> = remoteMovieUIState.mapLatest { movieState ->
        RemoteScreenUIState(
            remoteMovieUIState = movieState
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, RemoteScreenUIState.default)

}