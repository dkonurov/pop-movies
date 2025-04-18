package com.example.base.schedulers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class DispatcherProviderImpl : DispatcherProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun ui(): CoroutineDispatcher = Dispatchers.Main

    override fun computation(): CoroutineDispatcher = Dispatchers.Unconfined
}