package com.example.bottom.navigation.domain.models

import com.example.core.models.entity.LocalMovie

data class MovieResponse(
    val totalPage: Int,
    val movies: List<LocalMovie>
)