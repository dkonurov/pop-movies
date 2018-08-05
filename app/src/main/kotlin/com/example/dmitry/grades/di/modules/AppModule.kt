package com.example.dmitry.grades.di.modules

import android.content.Context
import android.os.Build
import com.example.dmitry.grades.BuildConfig
import com.example.dmitry.grades.di.BaseUrl
import com.example.dmitry.grades.di.DbName
import com.example.dmitry.grades.di.providers.DbProvider
import com.example.dmitry.grades.di.providers.FavoriteDaoProvider
import com.example.dmitry.grades.di.providers.MovieDaoProvider
import com.example.dmitry.grades.di.providers.PrivateDataSourceProvider
import com.example.dmitry.grades.domain.Logger
import com.example.dmitry.grades.domain.data.db.AppDatabase
import com.example.dmitry.grades.domain.data.db.FavoriteDao
import com.example.dmitry.grades.domain.data.db.MovieDao
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.models.config.AppInfo
import com.example.dmitry.grades.domain.repositories.AppInfoRepository
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import toothpick.config.Module

class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(String::class.java).withName(BaseUrl::class.java).toInstance(BuildConfig.PROD_URL)
        bind(String::class.java).withName(DbName::class.java).toInstance(BuildConfig.DB_NAME)
        bind(AppInfo::class.java).toInstance(AppInfo(BuildConfig.VERSION_CODE, BuildConfig.DEBUG, Build.VERSION.SDK_INT))
        bind(AppInfoRepository::class.java).to(AppInfoRepository::class.java)
        bind(PrivateDataSource::class.java).toProvider(PrivateDataSourceProvider::class.java).providesSingletonInScope()
        bind(ResourceRepository::class.java)
        bind(AppDatabase::class.java).toProvider(DbProvider::class.java).providesSingletonInScope()
        bind(MovieDao::class.java).toProvider(MovieDaoProvider::class.java).providesSingletonInScope()
        bind(FavoriteDao::class.java).toProvider(FavoriteDaoProvider::class.java).providesSingletonInScope()
        bind(Logger::class.java).toInstance(Logger())
        bind(MovieMapper::class.java).to(MovieMapper::class.java).instancesInScope()
    }
}