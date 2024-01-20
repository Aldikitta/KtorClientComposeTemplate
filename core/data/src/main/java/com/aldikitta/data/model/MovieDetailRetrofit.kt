package com.aldikitta.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

sealed interface MovieDetailRetrofit {
    data class MovieDetail(
        @SerializedName("adult")
        override val adult: Boolean,
        @SerializedName("backdrop_path")
        override val backdropPath: String?,
        @SerializedName("belongs_to_collection")
        val belongsToCollection: BelongsToCollection?,
        @SerializedName("budget")
        val budget: Int,
        @SerializedName("genres")
        val genres: List<Genre>,
        @SerializedName("homepage")
        val homepage: String?,
        @SerializedName("id")
        override val id: Int,
        @SerializedName("imdb_id")
        val imdbId: String?,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("overview")
        override val overview: String?,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("poster_path")
        override val posterPath: String?,

        //TODO production companies
        @SerializedName("production_companies")
        val productionCompanies: List<ProductionCompany?>,
        @SerializedName("production_countries")
        val productionCountries: List<ProductionCountry>,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("revenue")
        val revenue: Int,
        @SerializedName("runtime")
        val runtime: Int?,
        @SerializedName("spoken_languages")
        val spokenLanguages: List<SpokenLanguage>,
        @SerializedName("status")
        val status: String,
        @SerializedName("tagline")
        val tagline: String?,
        @SerializedName("title")
        override val title: String,
        @SerializedName("video")
        val video: Boolean,
        @SerializedName("vote_average")
        override val voteAverage: Float,
        @SerializedName("vote_count")
        override val voteCount: Int
    ): DetailPresentableMovie

    @Serializable
    data class BelongsToCollection(
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("poster_path")
        val posterPath: String
    )

    @Serializable
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )

    @Serializable
    data class ProductionCompany(
        @SerializedName("id")
        val id: Int,
        @SerializedName("logo_path")
        val logoPath: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("origin_country")
        val originCountry: String?
    )

    @Serializable
    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val iso31661: String,
        @SerializedName("name")
        val name: String
    )

    @Serializable
    data class SpokenLanguage(
        @SerializedName("english_name")
        val englishName: String,
        @SerializedName("iso_639_1")
        val iso6391: String,
        @SerializedName("name")
        val name: String
    )
}

