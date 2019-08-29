package com.example.di.providers

import com.example.base.schedulers.SchedulerProvider
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

class CoroutineScopeProvider @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val supervisor: CompletableJob
) : Provider<CoroutineScope> {

    override fun get(): CoroutineScope = CoroutineScope(schedulerProvider.ui() + supervisor)
}