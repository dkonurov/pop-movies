package com.example.splash.di

import com.example.base.schedulers.DispatcherProvider
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.preferences.PrivateDataSource
import com.example.di.BaseUIScope
import com.example.splash.domain.SplashNavigator

internal interface SplashDependencies {
    fun getSplashNavigator(): SplashNavigator
    fun getHttpDataSource(): HttpDataSource
    fun getPrivateDataSource(): PrivateDataSource
    fun getDispatcherProvider(): DispatcherProvider
}

internal class SplashDependenciesImpl : SplashDependencies {
    override fun getSplashNavigator(): SplashNavigator =
        BaseUIScope.getBaseUIScope().getInstance(SplashNavigator::class.java)

    override fun getHttpDataSource(): HttpDataSource =
        BaseUIScope.getBaseUIScope().getInstance(HttpDataSource::class.java)

    override fun getPrivateDataSource(): PrivateDataSource =
        BaseUIScope.getBaseUIScope().getInstance(PrivateDataSource::class.java)

    override fun getDispatcherProvider(): DispatcherProvider = BaseUIScope.getBaseUIScope().getInstance(DispatcherProvider::class.java)
}