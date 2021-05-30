package com.example.core.di

import com.example.core.data.logger.Logger

interface CoreDependencies {
    fun getLogger(): Logger
}

object CoreDependenicesFactories {
    fun create(): CoreDependencies = DaggerCoreDependenciesImpl.create()
}