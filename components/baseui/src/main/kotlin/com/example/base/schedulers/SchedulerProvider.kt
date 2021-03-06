package com.example.base.schedulers

import kotlinx.coroutines.CoroutineDispatcher

interface SchedulerProvider {

    fun io(): CoroutineDispatcher

    fun ui(): CoroutineDispatcher

    fun computation(): CoroutineDispatcher
}