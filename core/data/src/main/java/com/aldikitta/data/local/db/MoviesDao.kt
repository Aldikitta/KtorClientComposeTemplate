package com.aldikitta.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aldikitta.data.local.model.MovieEntity

@Dao
interface MoviesDao {
    @Query("SELECT * FROM MovieEntity WHERE language=:language")
    fun getAllMovies(language: String): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM MovieEntity WHERE language=:language")
    suspend fun deleteMoviesOfType(language: String)
}