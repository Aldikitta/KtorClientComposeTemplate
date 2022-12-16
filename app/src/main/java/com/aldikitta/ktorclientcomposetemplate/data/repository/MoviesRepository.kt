package com.aldikitta.ktorclientcomposetemplate.data.repository

import androidx.paging.PagingData
import com.aldikitta.ktorclientcomposetemplate.data.model.ContentLanguage
import com.aldikitta.ktorclientcomposetemplate.data.model.Movie
import com.aldikitta.ktorclientcomposetemplate.data.model.MovieResponse
import com.aldikitta.ktorclientcomposetemplate.data.remote.Resource
import dagger.Provides
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun popularMovies(
        contentLanguage: ContentLanguage = ContentLanguage.default
    ): Flow<PagingData<Movie>>
}