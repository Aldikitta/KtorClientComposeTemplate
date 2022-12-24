package com.aldikitta.data.repository

import androidx.paging.PagingData
import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun popularMovies(
        contentLanguage: ContentLanguage = ContentLanguage.default
    ): Flow<PagingData<Movie>>
}