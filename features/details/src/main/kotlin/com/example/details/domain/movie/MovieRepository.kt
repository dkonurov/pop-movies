package com.example.details.domain.movie

import com.example.details.view.ViewMovie

internal interface MovieRepository {
    suspend fun findMovie(id: Long): ViewMovie

    suspend fun saveFavorite(viewMovie: ViewMovie)

    suspend fun removeFavorite(viewMovie: ViewMovie)
}