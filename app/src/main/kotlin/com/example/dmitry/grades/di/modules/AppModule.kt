package com.example.dmitry.grades.di.modules

import android.content.Context
import com.example.core.data.logger.Logger
import com.example.core.di.CoreDependencies
import com.example.core.network.di.RemoteContainer
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.config.Config
import com.example.core.storage.db.inteface.FavoriteDao
import com.example.core.storage.db.inteface.MovieDao
import com.example.core.storage.di.container.StorageContainer
import com.example.core.storage.preferences.ErrorMessageDataSource
import com.example.core.storage.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.navigators.SplashNavigatorImpl
import com.example.splash.domain.SplashNavigator
import toothpick.config.Module

class AppModule(
    context: Context,
    remoteContainer: RemoteContainer,
    storageContainer: StorageContainer,
    coreDependencies: CoreDependencies
) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(HttpDataSource::class.java).toInstance(remoteContainer.getHttpDataSource())
        bind(FavoriteDao::class.java).toInstance(storageContainer.getFavoriteDao())
        bind(MovieDao::class.java).toInstance(storageContainer.getMovieDao())
        bind(PrivateDataSource::class.java).toInstance(storageContainer.getPrivateDataSource())
        bind(Logger::class.java).toInstance(coreDependencies.getLogger())
        bind(ErrorMessageDataSource::class.java).toInstance(storageContainer.getErrorMessageDataSource())
        bind(Config::class.java).toInstance(storageContainer.getConfig())
        bind(SplashNavigator::class.java).toInstance(SplashNavigatorImpl())
    }
}