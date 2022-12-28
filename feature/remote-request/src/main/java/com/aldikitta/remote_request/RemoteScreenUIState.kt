package com.aldikitta.remote_request

import androidx.paging.PagingData
import com.aldikitta.data.model.Movie
import com.aldikitta.data.model.PresentableMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class RemoteScreenUIState(
    val remoteMovieUIState: RemoteMovieUIState
) {
    companion object {
        val default: RemoteScreenUIState = RemoteScreenUIState(
            remoteMovieUIState = RemoteMovieUIState.default
        )
    }
}

data class RemoteMovieUIState(
    val popular: Flow<PagingData<Movie>>
) {
    companion object {
        val default: RemoteMovieUIState = RemoteMovieUIState(
            popular = emptyFlow()
        )
    }
}

sealed class PresentableRemoteItemState {
    object Loading : PresentableRemoteItemState()
    object Error : PresentableRemoteItemState()
    data class Result(val movie: PresentableMovie) : PresentableRemoteItemState()
}