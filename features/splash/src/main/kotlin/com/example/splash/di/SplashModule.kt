package com.example.splash.di

import com.example.splash.domain.repositories.configurtaion.BaseConfigRepository
import com.example.splash.domain.repositories.configurtaion.BaseConfigRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal interface SplashModule {
    @Binds
    fun bindBaseConfigRepository(impl: BaseConfigRepositoryImpl): BaseConfigRepository
}