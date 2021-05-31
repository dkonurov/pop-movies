package com.example.core.di

import com.example.core.data.logger.Logger
import kotlinx.coroutines.CoroutineScope

interface CoreDependencies {
    fun getLogger(): Logger
    fun getCoroutineScope(): CoroutineScope
}

object CoreDependenicesFactories {
    fun create(): CoreDependencies = DaggerCoreDependenciesImpl.create()
}