package com.example.navigation.di

import com.example.bottom.navigation.domain.config.SortConfigRepository
import com.example.bottom.navigation.domain.config.SortConfigRepositoryImpl
import com.example.favorite.list.view.FavoriteListRouter
import com.example.movie.list.view.MovieListRouter
import com.example.navigation.ChildMovieRouterImpl
import toothpick.config.Module

class BottomNavigationModule : Module() {

    init {
        bind(SortConfigRepository::class.java).to(SortConfigRepositoryImpl::class.java)
        bind(MovieListRouter::class.java).to(ChildMovieRouterImpl::class.java)
        bind(FavoriteListRouter::class.java).to(ChildMovieRouterImpl::class.java)
    }
}