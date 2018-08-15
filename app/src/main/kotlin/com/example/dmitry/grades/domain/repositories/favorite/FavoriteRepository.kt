package com.example.dmitry.grades.domain.repositories.favorite

import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import io.reactivex.Single

interface FavoriteRepository {

    fun saveFavorite(viewMovie: ViewMovie)

    fun removeFavorite(viewMovie: ViewMovie)

    fun getFavorites(page: Int = 1): Single<MovieListInfo>
}