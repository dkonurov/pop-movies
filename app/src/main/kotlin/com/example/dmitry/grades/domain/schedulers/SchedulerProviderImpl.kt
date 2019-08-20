package com.example.dmitry.grades.domain.schedulers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SchedulerProviderImpl : SchedulerProvider {

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun ui(): CoroutineDispatcher = Dispatchers.Main

    override fun computation(): CoroutineDispatcher = Dispatchers.Unconfined
}