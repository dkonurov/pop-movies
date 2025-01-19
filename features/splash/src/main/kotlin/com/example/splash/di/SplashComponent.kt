package com.example.splash.di

import com.example.splash.domain.SplashNavigator
import com.example.splash.ui.SplashViewModel
import dagger.Component
import javax.inject.Provider

@Component(dependencies = [SplashDependencies::class])
internal interface SplashComponent {
    fun getSplashViewModel(): Provider<SplashViewModel>

    fun getSplashNavigator(): SplashNavigator

    @Component.Factory
    interface Factory {
        fun create(dependencies: SplashDependencies): SplashComponent
    }
}