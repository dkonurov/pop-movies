package com.example.dmitry.grades.di.modules

import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.navigators.SplashNavigatorImpl
import com.example.dmitry.grades.domain.repositories.AppInfoRepository
import com.example.splash.domain.SplashNavigator
import toothpick.config.Module

class AppModule : Module() {

    init {
        bind(AppInfoRepository::class.java).to(AppInfoRepository::class.java)
        bind(MovieMapper::class.java).to(MovieMapper::class.java).singleton()
        bind(SplashNavigator::class.java).to(SplashNavigatorImpl::class.java)
    }
}