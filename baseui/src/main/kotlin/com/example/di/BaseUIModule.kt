package com.example.di

import com.example.base.schedulers.SchedulerProvider
import com.example.base.schedulers.SchedulerProviderImpl
import com.example.di.providers.CoroutineScopeProvider
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import toothpick.config.Module

class BaseUIModule : Module() {

    init {
        // schedulers
        bind(SchedulerProvider::class.java).toInstance(SchedulerProviderImpl())

        // scopes
        bind(CompletableJob::class.java).toProviderInstance { SupervisorJob() }
        bind(CoroutineScope::class.java).toProvider(CoroutineScopeProvider::class.java)
    }
}