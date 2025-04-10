package com.example.bottom.navigation.domain.mappers

import com.example.bottom.navigation.domain.models.MoviePage
import com.example.bottom.navigation.ui.models.MovieListInfo
import com.example.core.storage.preferences.PrivateDataSource
import javax.inject.Inject

class MovieListMapper
@Inject constructor(
    private val privateDataSource: PrivateDataSource
) {

    fun toMovieListInfo(
        response: MoviePage,
        page: Int
    ): MovieListInfo {
        updatePoster(response)
        return MovieListInfo(response.totalPage, response.movies, page)
    }

    private fun updatePoster(response: MoviePage) {
        val pathImg = privateDataSource.baseUrlImg
        val sizeImg = privateDataSource.posterSize
        response.movies.forEach { movie ->
            if (movie.posterPath == null || pathImg == null || sizeImg == null) {
                movie.posterPath = null
            } else {
                movie.posterPath = "$pathImg$sizeImg${movie.posterPath}"
            }
        }
    }
}