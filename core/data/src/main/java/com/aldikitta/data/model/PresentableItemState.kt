package com.aldikitta.data.model

sealed class PresentableItemState {
    object Loading : PresentableItemState()
    object Error : PresentableItemState()
    data class Result(val movie: Movie) : PresentableItemState()
}