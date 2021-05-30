package com.example.dmitry.grades.di.modules

import android.content.Context
import com.example.core.data.config.Config
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.db.inteface.MovieDao
import com.example.core.data.logger.Logger
import com.example.core.data.message.ErrorMessageDataSource
import com.example.core.data.preferences.PrivateDataSource
import com.example.core.di.CoreDependencies
import com.example.core.network.remote.HttpDataSource
import com.example.dmitry.grades.domain.navigators.SplashNavigatorImpl
import com.example.splash.domain.SplashNavigator
import toothpick.config.Module

class AppModule(coreDependencies: CoreDependencies) : Module() {

    init {
        bind(Context::class.java).toInstance(coreDependencies.getContext())
        bind(FavoriteDao::class.java).toInstance(coreDependencies.getFavoriteDao())
        bind(MovieDao::class.java).toInstance(coreDependencies.getMovieDao())
        bind(PrivateDataSource::class.java).toInstance(coreDependencies.getPrivateDataSource())
        bind(Logger::class.java).toInstance(coreDependencies.getLogger())
        bind(ErrorMessageDataSource::class.java).toInstance(coreDependencies.getErrorMessageDataSource())
        bind(HttpDataSource::class.java).toInstance(coreDependencies.getHttpDataSource())
        bind(Config::class.java).toInstance(coreDependencies.getConfig())
        bind(SplashNavigator::class.java).to(SplashNavigatorImpl::class.java)
    }
}