package com.example.core.di

import android.content.Context
import com.example.core.di.module.CoreModule
import com.example.core.di.module.InterceptorsModule
import com.example.core.di.module.RemoteModule
import com.example.core.di.scope.CoreScope
import dagger.BindsInstance
import dagger.Component

@Component(modules = [InterceptorsModule::class, CoreModule::class, RemoteModule::class])
@CoreScope
internal interface CoreDependenciesImpl : CoreDependencies {

    @Component.Factory
    interface FactoryImpl {
        fun create(@BindsInstance context: Context): CoreDependenciesImpl
    }
}