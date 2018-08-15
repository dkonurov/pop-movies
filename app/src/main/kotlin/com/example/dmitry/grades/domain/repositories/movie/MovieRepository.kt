package com.example.dmitry.grades.domain.repositories.movie

import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import io.reactivex.Single

interface MovieRepository {

    fun getMovies(page: Int = 1,
                       sortBy: String? = null): Single<MovieListInfo>

    fun findMovie(id: Long): Single<ViewMovie>

    fun clearCache()
}