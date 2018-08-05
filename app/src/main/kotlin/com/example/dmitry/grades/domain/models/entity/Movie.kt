package com.example.dmitry.grades.domain.models.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Movie.TABLE_NAME)
data class Movie(val id: Long,
                 @PrimaryKey(autoGenerate = true)
                 val localId: Long,
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
                 @SerializedName("vote_average") val voteAverage: Double) {
    companion object {
        const val TABLE_NAME = "Movie"
    }
}