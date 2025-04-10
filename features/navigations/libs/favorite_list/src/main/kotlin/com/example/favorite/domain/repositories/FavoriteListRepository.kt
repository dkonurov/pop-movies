package com.example.favorite.domain.repositories

import com.example.bottom.navigation.domain.models.MoviePage

internal interface FavoriteListRepository {

    suspend fun getFavorites(page: Int = 1): MoviePage
}