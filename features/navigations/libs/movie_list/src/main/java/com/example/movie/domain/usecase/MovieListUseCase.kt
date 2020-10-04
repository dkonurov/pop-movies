package com.example.movie.domain.usecase

import com.example.bottom.navigation.domain.mappers.MovieListMapper
import com.example.bottom.navigation.ui.models.MovieListInfo
import com.example.movie.domain.repositories.MovieListRepository
import javax.inject.Inject

internal class MovieListUseCase
@Inject constructor(
    private val movieListMapper: MovieListMapper,
    private val movieListRepository: MovieListRepository
) {

    suspend fun getMovies(page: Int, sortBy: String?): MovieListInfo {
        val response = movieListRepository.getMovies(page, sortBy)
        return movieListMapper.toMovieListInfo(response, page)
    }

    suspend fun clearCache() {
        movieListRepository.clearCache()
    }
}