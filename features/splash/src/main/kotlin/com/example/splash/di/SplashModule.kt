package com.example.splash.di

import com.example.splash.domain.repositories.configurtaion.BaseConfigRepository
import com.example.splash.domain.repositories.configurtaion.BaseConfigRepositoryImpl
import com.example.splash.ui.SplashViewModel
import toothpick.config.Module

internal class SplashModule : Module() {

    init {
        bind(BaseConfigRepository::class.java).to(BaseConfigRepositoryImpl::class.java)
        bind(SplashViewModel::class.java).to(SplashViewModel::class.java).singleton()
    }
}