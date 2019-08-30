package com.example.favorite.domain.repositories

import com.example.bottom.navigation.domain.models.MovieResponse

internal interface FavoriteListRepository {

    suspend fun getFavorites(page: Int = 1): MovieResponse
}