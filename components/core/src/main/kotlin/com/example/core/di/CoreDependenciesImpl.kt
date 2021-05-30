package com.example.core.di

import android.content.Context
import com.example.core.di.module.CoreModule
import com.example.core.di.scope.CoreScope
import com.example.core.network.di.RemoteDependencies
import dagger.BindsInstance
import dagger.Component

@Component(modules = [CoreModule::class], dependencies = [RemoteDependencies::class])
@CoreScope
internal interface CoreDependenciesImpl : CoreDependencies {

    @Component.Factory
    interface FactoryImpl {
        fun create(
            @BindsInstance context: Context,
            remoteDependencies: RemoteDependencies
        ): CoreDependenciesImpl
    }
}