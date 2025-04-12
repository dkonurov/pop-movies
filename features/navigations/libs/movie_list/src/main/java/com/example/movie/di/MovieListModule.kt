package com.example.movie.di

import com.example.movie.domain.repositories.MovieListRepository
import com.example.movie.list.ListViewModel
import toothpick.config.Module

internal class MovieListModule : Module() {

    init {
        bind(ListViewModel::class.java).to(ListViewModel::class.java).singleton()
        bind(MovieListRepository::class.java).singleton()
    }
}