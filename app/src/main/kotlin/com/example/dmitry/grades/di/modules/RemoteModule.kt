package com.example.dmitry.grades.di.modules

import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import toothpick.config.Module
import javax.inject.Inject

class RemoteModule @Inject constructor() : Module() {

    init {
        //repostories
        bind(MovieRepository::class.java).to(MovieRepositoryImpl::class.java)
    }
}