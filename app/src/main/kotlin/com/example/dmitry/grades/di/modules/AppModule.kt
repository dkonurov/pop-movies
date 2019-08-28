package com.example.dmitry.grades.di.modules

import com.example.dmitry.grades.domain.Logger
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.repositories.AppInfoRepository
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import toothpick.config.Module

class AppModule : Module() {

    init {
        bind(AppInfoRepository::class.java).to(AppInfoRepository::class.java)
        bind(ResourceRepository::class.java)
        bind(Logger::class.java).toInstance(Logger())
        bind(MovieMapper::class.java).to(MovieMapper::class.java).instancesInScope()
    }
}