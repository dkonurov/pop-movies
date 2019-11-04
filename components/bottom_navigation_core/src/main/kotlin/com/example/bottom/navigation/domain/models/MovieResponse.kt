package com.example.bottom.navigation.domain.models

import com.example.core.models.entity.Movie

data class MovieResponse(
    val totalPage: Int,
    val movies: List<Movie>
)