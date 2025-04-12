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

    suspend fun getMovies(args: Args): MovieListInfo {
        val response = movieListRepository.getMovies(args.page, args.sortBy)
        return movieListMapper.toMovieListInfo(response, args.page)
    }

    data class Args(
        val page: Int,
        val sortBy: String? = null,
    )
}