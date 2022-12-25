package com.aldikitta.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val language: String,
    val nextPage: Int?,
    val lastUpdated: Long
)