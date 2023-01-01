package com.aldikitta.domain.di

import com.aldikitta.domain.usecase.movie_details.GetMovieDetailsUseCase
import com.aldikitta.domain.usecase.movie_details.GetMovieDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseBinds {
    @Binds
    fun provideMovieDetailUseCase(
        impl: GetMovieDetailsUseCaseImpl
    ): GetMovieDetailsUseCase
}