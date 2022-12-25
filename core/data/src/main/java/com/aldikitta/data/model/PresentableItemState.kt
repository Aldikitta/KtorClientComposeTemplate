package com.aldikitta.data.model

import com.aldikitta.data.local.model.MovieEntity

sealed class PresentableItemState {
    object Loading : PresentableItemState()
    object Error : PresentableItemState()
    data class Result(val movie: Movie) : PresentableItemState()
}

sealed class PresentableItemLocalState {
    object Loading : PresentableItemLocalState()
    object Error : PresentableItemLocalState()
    data class Result(val movie: MovieEntity) : PresentableItemLocalState()
}