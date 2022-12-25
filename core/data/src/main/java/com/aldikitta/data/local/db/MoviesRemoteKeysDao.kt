package com.aldikitta.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aldikitta.data.local.model.MoviesRemoteKeys

@Dao
interface MoviesRemoteKeysDao {
    @Query("SELECT * FROM MoviesRemoteKeys WHERE language=:language")
    suspend fun getRemoteKey(language: String): MoviesRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: MoviesRemoteKeys)

    @Query("DELETE FROM MoviesRemoteKeys WHERE language=:language")
    suspend fun deleteRemoteKeysOfType(language: String)
}