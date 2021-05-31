package com.example.dmitry.grades.di.modules

import android.content.Context
import com.example.core.data.logger.Logger
import com.example.core.di.CoreDependencies
import com.example.core.network.di.RemoteContainer
import com.example.core.network.remote.HttpDataSource
import com.example.core.network.schedulers.SchedulerProvider
import com.example.dmitry.grades.domain.navigators.SplashNavigatorImpl
import com.example.splash.domain.SplashNavigator
import com.example.storage.config.Config
import com.example.storage.db.inteface.FavoriteDao
import com.example.storage.db.inteface.MovieDao
import com.example.storage.di.container.StorageContainer
import com.example.storage.preferences.ErrorMessageDataSource
import com.example.storage.preferences.PrivateDataSource
import kotlinx.coroutines.CoroutineScope
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
        bind(SchedulerProvider::class.java).toInstance(remoteContainer.getSchedulerProvider())
        bind(FavoriteDao::class.java).toInstance(storageContainer.getFavoriteDao())
        bind(MovieDao::class.java).toInstance(storageContainer.getMovieDao())
        bind(PrivateDataSource::class.java).toInstance(storageContainer.getPrivateDataSource())
        bind(ErrorMessageDataSource::class.java).toInstance(storageContainer.getErrorMessageDataSource())
        bind(Config::class.java).toInstance(storageContainer.getConfig())
        bind(Logger::class.java).toInstance(coreDependencies.getLogger())
        bind(CoroutineScope::class.java).toProviderInstance { coreDependencies.getCoroutineScope() }
        bind(SplashNavigator::class.java).to(SplashNavigatorImpl::class.java)
    }
}