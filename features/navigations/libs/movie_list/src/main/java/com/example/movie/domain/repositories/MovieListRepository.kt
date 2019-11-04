package com.example.movie.domain.repositories

import com.example.bottom.navigation.domain.models.MovieResponse

internal interface MovieListRepository {

    suspend fun getMovies(
        page: Int = 1,
        sortBy: String? = null
    ): MovieResponse

    suspend fun clearCache()
}