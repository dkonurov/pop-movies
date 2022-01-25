package com.example.splash.di

import com.example.splash.ui.SplashViewModel
import dagger.Component
import javax.inject.Provider

@Component(modules = [SplashModule::class], dependencies = [SplashDependencies::class])
internal interface SplashComponent {
    fun getSplashViewModel(): Provider<SplashViewModel>

    @Component.Factory
    interface Factory {
        fun create(dependencies: SplashDependencies): SplashComponent
    }
}