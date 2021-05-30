package com.example.core.network.di

import com.example.core.network.remote.HttpDataSource

interface RemoteDependencies {
    fun getHttpDataSource(): HttpDataSource
}


class RemoteDependenciesFactory {
    fun create(url: String, key: String): RemoteDependencies {
        return DaggerRemoteDependenciesImpl.factory().create(url, key)
    }
}