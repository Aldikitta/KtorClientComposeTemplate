package com.aldikitta.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("adult")
    override val adult: Boolean,
    @SerialName("backdrop_path")
    override val backdropPath: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("id")
    override val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    override val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    override val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("title")
    override val title: String,
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    override val voteAverage: Float,
    @SerialName("vote_count")
    override val voteCount: Int
) : DetailPresentableMovie