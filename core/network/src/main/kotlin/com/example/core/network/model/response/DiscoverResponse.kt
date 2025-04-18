package com.example.core.network.model.response

import com.example.core.network.model.MovieDTO
import com.google.gson.annotations.SerializedName

data class DiscoverResponse(
    val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val movies: List<MovieDTO>,
)