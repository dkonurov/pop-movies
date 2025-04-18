package com.example.di

import com.example.base.schedulers.DispatcherProvider
import com.example.base.schedulers.DispatcherProviderImpl
import com.example.di.providers.CoroutineScopeProvider
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import toothpick.config.Module

class RemoteModule : Module() {
    init {
        // schedulers
        bind(DispatcherProvider::class.java).toInstance(DispatcherProviderImpl())

        // scopes
        bind(CompletableJob::class.java).toProviderInstance { SupervisorJob() }
        bind(CoroutineScope::class.java).toProvider(CoroutineScopeProvider::class.java)
    }
}