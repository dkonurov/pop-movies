package com.example.favorite.domain.usecase

import com.example.bottom.navigation.domain.mappers.MovieListMapper
import com.example.bottom.navigation.ui.models.MovieListInfo
import com.example.favorite.domain.repositories.FavoriteListRepository
import javax.inject.Inject

internal class FavoriteListUseCase
    @Inject
    constructor(
        private val favoriteListRepository: FavoriteListRepository,
        private val movieListMapper: MovieListMapper,
    ) {
        suspend fun getFavorites(page: Int): MovieListInfo {
            val response = favoriteListRepository.getFavorites(page)
            return movieListMapper.toMovieListInfo(response, page)
        }
    }