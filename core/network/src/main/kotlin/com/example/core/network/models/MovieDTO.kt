package com.example.core.network.models

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    val id: Long,
    @SerializedName("poster_path") var posterPath: String?,
    val adult: Boolean,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    val title: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("video") val isVideo: Boolean,
    @SerializedName("vote_average") val voteAverage: Double
)