package com.aldikitta.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["language"])])
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val title: String,
    val language: String
) {
    val fullLocalPosterPath: String
        get() = "https://image.tmdb.org/t/p/original/$posterPath"
}