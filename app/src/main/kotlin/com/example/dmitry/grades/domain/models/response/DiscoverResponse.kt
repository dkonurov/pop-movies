package com.example.dmitry.grades.domain.models.response

import com.example.core.models.entity.Movie
import com.google.gson.annotations.SerializedName

data class DiscoverResponse(
    val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val movies: List<Movie>
)