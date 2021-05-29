package com.example.core.di

import android.content.Context
import com.example.core.data.config.Config
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.db.inteface.MovieDao
import com.example.core.data.logger.Logger
import com.example.core.data.message.ErrorMessageDataSource
import com.example.core.data.preferences.PrivateDataSource
import com.example.core.data.remote.HttpDataSource

interface CoreDependencies {
    fun getContext(): Context
    fun getFavoriteDao(): FavoriteDao
    fun getMovieDao(): MovieDao
    fun getPrivateDataSource(): PrivateDataSource
    fun getLogger(): Logger
    fun getErrorMessageDataSource(): ErrorMessageDataSource
    fun getHttpDataSource(): HttpDataSource
    fun getConfig(): Config
}


class CoreDependenicesFactories {
    fun create(context: Context): CoreDependencies = DaggerCoreDependenciesImpl.factory().create(context)
}