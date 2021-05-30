package com.example.core.network.di

import com.example.core.network.remote.HttpDataSource

interface RemoteContainer {
    fun getHttpDataSource(): HttpDataSource
}

object RemoteContainerFactory {
    fun create(url: String, key: String): RemoteContainer {
        return DaggerRemoteContainerImpl.factory().create(url, key)
    }
}