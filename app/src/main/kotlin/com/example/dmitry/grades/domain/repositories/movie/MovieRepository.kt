package com.example.dmitry.grades.domain.repositories.movie

import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie

interface MovieRepository {

    suspend fun getMovies(page: Int = 1,
                          sortBy: String? = null): MovieListInfo

    suspend fun findMovie(id: Long): ViewMovie

    suspend fun clearCache()
}