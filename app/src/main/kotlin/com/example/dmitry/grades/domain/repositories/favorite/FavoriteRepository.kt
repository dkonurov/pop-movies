package com.example.dmitry.grades.domain.repositories.favorite

import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie

interface FavoriteRepository {

    suspend fun saveFavorite(viewMovie: ViewMovie)

    suspend fun removeFavorite(viewMovie: ViewMovie)

    suspend fun getFavorites(page: Int = 1): MovieListInfo
}