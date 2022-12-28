package com.aldikitta.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.aldikitta.data.model.PresentableMovie

@Entity(indices = [Index(value = ["language"])])
data class MovieEntity(
    override val id: Int,
    val originalTitle: String,
    val overview: String,
    override val posterPath: String,
    override val title: String,
    val language: String
) : PresentableMovie {
    @PrimaryKey(autoGenerate = true)
    var entityId: Int = 0
 }