package com.example.bottom.navigation.domain.models

import com.example.core.storage.db.entity.LocalMovie

data class MoviePage(
    val totalPage: Int,
    val movies: List<LocalMovie>,
)