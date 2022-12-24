package com.aldikitta.di

import com.aldikitta.data.repository.ConfigRepository
import com.aldikitta.data.repository.ConfigRepositoryImpl
import com.aldikitta.data.repository.MoviesRepository
import com.aldikitta.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBinds {
    @Binds
    @Singleton
    fun bindConfigRepository(impl: ConfigRepositoryImpl): ConfigRepository

    @Binds
    @Singleton
    fun bindMovieRepository(impl: MoviesRepositoryImpl): MoviesRepository
}
