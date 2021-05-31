package com.example.core.network.di

import com.example.core.network.remote.HttpDataSource
import com.example.core.network.schedulers.SchedulerProvider

interface RemoteContainer {
    fun getHttpDataSource(): HttpDataSource
    fun getSchedulerProvider(): SchedulerProvider
}

object RemoteContainerFactory {
    fun create(url: String, key: String): RemoteContainer {
        return DaggerRemoteContainerImpl.factory().create(url, key)
    }
}