package com.aldikitta.movie_detail

import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.DetailPresentableMovie
import com.aldikitta.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DetailUIState(
    val movieDetailUIState: MovieDetailUIState
)

data class LanguageUIState(
    val contentLanguage: ContentLanguage

) {
    companion object {
        val default: LanguageUIState = LanguageUIState(
            contentLanguage = ContentLanguage(
                languageCode = "",
                region = ""
            )
        )
    }
}

sealed interface MovieDetailUIState {
    object Nothing : MovieDetailUIState
    object Loading : MovieDetailUIState
    data class Success(
        val movieDetail: DetailPresentableMovie
    ) : MovieDetailUIState

    data class Error(val error: String) : MovieDetailUIState
}
//
//sealed interface LanguageUIState {
//    object Success : LanguageUIState
//}

sealed interface TestState {
    data class Language(val language: ContentLanguage) : TestState
}
