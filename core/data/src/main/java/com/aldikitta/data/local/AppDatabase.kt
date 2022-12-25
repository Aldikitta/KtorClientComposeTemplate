package com.aldikitta.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aldikitta.data.local.db.MoviesDao
import com.aldikitta.data.local.db.MoviesRemoteKeysDao
import com.aldikitta.data.local.model.MovieEntity
import com.aldikitta.data.local.model.MoviesRemoteKeys

@Database(
    entities = [
        MovieEntity::class,
        MoviesRemoteKeys::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun moviesRemoteKeysDao(): MoviesRemoteKeysDao
}