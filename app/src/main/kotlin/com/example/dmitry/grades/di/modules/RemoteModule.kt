package com.example.dmitry.grades.di.modules

import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepository
import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepositoryImpl
import com.example.dmitry.grades.domain.repositories.favorite.FavoriteRepository
import com.example.dmitry.grades.domain.repositories.favorite.FavoriteRepositoryImpl
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import toothpick.config.Module
import javax.inject.Inject

class RemoteModule @Inject constructor() : Module() {

    init {
        //repostories
        bind(MovieRepository::class.java).to(MovieRepositoryImpl::class.java)
        bind(BaseConfigRepository::class.java).to(BaseConfigRepositoryImpl::class.java)
        bind(FavoriteRepository::class.java).to(FavoriteRepositoryImpl::class.java)
    }
}