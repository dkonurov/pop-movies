package com.example.dmitry.grades.di.modules

import com.example.dmitry.grades.domain.navigators.SplashNavigatorImpl
import com.example.splash.domain.SplashNavigator
import toothpick.config.Module

class AppModule : Module() {

    init {
        bind(SplashNavigator::class.java).to(SplashNavigatorImpl::class.java)
    }
}