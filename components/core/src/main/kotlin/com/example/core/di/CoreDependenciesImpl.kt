package com.example.core.di

import com.example.core.di.module.CoreModule
import com.example.core.di.scope.CoreScope
import dagger.Component

@Component(modules = [CoreModule::class])
@CoreScope
internal interface CoreDependenciesImpl : CoreDependencies