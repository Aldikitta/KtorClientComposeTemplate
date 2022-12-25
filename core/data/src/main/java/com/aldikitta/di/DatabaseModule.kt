package com.aldikitta.di

import android.content.Context
import androidx.room.Room
import com.aldikitta.data.local.AppDatabase
import com.aldikitta.data.local.db.MoviesDao
import com.aldikitta.data.local.db.MoviesRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesMoviesDao(database: AppDatabase): MoviesDao = database.moviesDao()

    @Provides
    fun providesMoviesRemoteKeys(database: AppDatabase): MoviesRemoteKeysDao = database.moviesRemoteKeysDao()
}