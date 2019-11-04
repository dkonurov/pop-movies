package com.example.navigation

import androidx.lifecycle.LiveData
import com.example.favorite.list.view.FavoriteListRouter
import com.example.movie.list.view.MovieListRouter
import javax.inject.Inject

interface MovieDetailsRouter {

    val screens: LiveData<Long>

    fun showDetails(movieId: Long)
}

class ChildMovieRouterImpl @Inject constructor(
    private val movieDetailsRouter: MovieDetailsRouter
) : MovieListRouter, FavoriteListRouter {

    override fun showDetails(movieId: Long) {
        movieDetailsRouter.showDetails(movieId)
    }

}