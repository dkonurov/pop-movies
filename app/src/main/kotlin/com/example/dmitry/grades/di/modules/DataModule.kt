package com.example.dmitry.grades.di.modules

import android.content.Context
import android.os.Build
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.db.inteface.IDatabase
import com.example.core.data.db.inteface.MovieDao
import com.example.core.data.preferences.PrivateDataSource
import com.example.core.data.remote.HttpDataSource
import com.example.core.models.config.ServerInfo
import com.example.dmitry.grades.BuildConfig
import com.example.dmitry.grades.di.ApiKey
import com.example.dmitry.grades.di.BaseUrl
import com.example.dmitry.grades.di.DbName
import com.example.dmitry.grades.di.providers.DbProvider
import com.example.dmitry.grades.di.providers.FavoriteDaoProvider
import com.example.dmitry.grades.di.providers.HttpDataSourceProvider
import com.example.dmitry.grades.di.providers.MovieDaoProvider
import com.example.dmitry.grades.di.providers.PrivateDataSourceProvider
import com.example.dmitry.grades.domain.models.config.AppInfo
import toothpick.config.Module

class DataModule(context: Context) : Module() {

    init {
        //data
        bind(Context::class.java).toInstance(context)
        bind(String::class.java).withName(BaseUrl::class.java).toInstance(BuildConfig.PROD_URL)
        bind(String::class.java).withName(DbName::class.java).toInstance(BuildConfig.DB_NAME)
        val appInfo = AppInfo(BuildConfig.VERSION_CODE, BuildConfig.DEBUG, Build.VERSION.SDK_INT)
        bind(AppInfo::class.java).toInstance(appInfo)

        //database
        bind(IDatabase::class.java).toProvider(DbProvider::class.java).providesSingletonInScope()
        bind(MovieDao::class.java).toProvider(MovieDaoProvider::class.java)
                .providesSingletonInScope()
        bind(FavoriteDao::class.java).toProvider(FavoriteDaoProvider::class.java)
                .providesSingletonInScope()

        //private
        bind(PrivateDataSource::class.java).toProvider(PrivateDataSourceProvider::class.java)
                .providesSingletonInScope()

        //remote
        bind(String::class.java).withName(ApiKey::class.java).toInstance(BuildConfig.APY_KEY)
        bind(ServerInfo::class.java).toInstance(ServerInfo(30, 30, 30))
        bind(HttpDataSource::class.java).toProvider(HttpDataSourceProvider::class.java)
                .providesSingletonInScope()
    }
}