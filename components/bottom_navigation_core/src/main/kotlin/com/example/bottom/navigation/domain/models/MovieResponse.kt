package com.example.bottom.navigation.domain.models

import com.example.storage.db.entity.LocalMovie

data class MovieResponse(
    val totalPage: Int,
    val movies: List<LocalMovie>
)