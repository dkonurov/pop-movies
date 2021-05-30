package com.example.core.models

import androidx.room.PrimaryKey

data class LocalDetailsMovie(
    val id: Long,
    @PrimaryKey(autoGenerate = true)
    val localId: Long,
    var posterPath: String?,
    val adult: Boolean,
    val overview: String,
    var releaseDate: String,
    val genreIds: List<Int>,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: String?,
    val popularity: Double,
    val voteCount: Int,
    val isVideo: Boolean,
    val runtime: String?,
    val voteAverage: Double
)