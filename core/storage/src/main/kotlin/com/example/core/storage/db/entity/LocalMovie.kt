package com.example.core.storage.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LocalMovie.TABLE_NAME)
data class LocalMovie(
    @PrimaryKey
    val id: Long,
    var posterPath: String?,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String,
    val genreIds: List<Int>,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: String?,
    val popularity: Double,
    val voteCount: Int,
    val isVideo: Boolean,
    val voteAverage: Double,
    val page: Int
) {
    companion object {
        const val TABLE_NAME = "Movie"
    }
}