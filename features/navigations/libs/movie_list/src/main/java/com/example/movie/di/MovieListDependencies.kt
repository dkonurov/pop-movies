package com.example.movie.di

import com.example.base.schedulers.DispatcherProvider
import com.example.core.data.logger.Logger
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.config.Config
import com.example.core.storage.db.inteface.MovieDao
import com.example.core.storage.preferences.ErrorMessageDataSource
import com.example.core.storage.preferences.PrivateDataSource
import com.example.movie.list.view.MovieListRouter
import toothpick.Scope

internal interface MovieListDependencies {
    fun router(): MovieListRouter

    fun getHttpDataSource(): HttpDataSource

    fun getDispatcherProvider(): DispatcherProvider

    fun errorMessageDataSource(): ErrorMessageDataSource

    fun logger(): Logger

    fun privateDataSource(): PrivateDataSource

    fun movieDao(): MovieDao

    fun config(): Config
}

internal class MovieListDependenciesImpl(
    private val scope: Scope,
) : MovieListDependencies {
    override fun router(): MovieListRouter = scope.getInstance(MovieListRouter::class.java)

    override fun getHttpDataSource(): HttpDataSource = scope.getInstance(HttpDataSource::class.java)

    override fun getDispatcherProvider(): DispatcherProvider = scope.getInstance(DispatcherProvider::class.java)

    override fun errorMessageDataSource(): ErrorMessageDataSource = scope.getInstance(ErrorMessageDataSource::class.java)

    override fun logger(): Logger = scope.getInstance(Logger::class.java)

    override fun privateDataSource(): PrivateDataSource = scope.getInstance(PrivateDataSource::class.java)

    override fun movieDao(): MovieDao = scope.getInstance(MovieDao::class.java)

    override fun config(): Config = scope.getInstance(Config::class.java)
}