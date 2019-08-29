package com.example.splash.di

import com.example.splash.domain.repositories.configurtaion.BaseConfigRepository
import com.example.splash.domain.repositories.configurtaion.BaseConfigRepositoryImpl
import toothpick.config.Module

class SplashModule : Module() {

    init {
        bind(BaseConfigRepository::class.java).to(BaseConfigRepositoryImpl::class.java)
    }
}