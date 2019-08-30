package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.config.Config
import com.example.core.data.db.AppDatabase
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.db.inteface.MovieDao
import com.example.core.data.logger.Logger
import com.example.core.data.logger.LoggerImpl
import com.example.core.data.message.ErrorMessageDataSource
import com.example.core.data.message.ErrorMessageDataSourceImpl
import com.example.core.data.preferences.PrivateDataSource
import com.example.core.data.preferences.PrivateDataSourceImpl
import com.example.core.data.remote.HttpDataSource
import com.example.core.di.providers.HttpDataSourceProvider
import com.google.gson.Gson
import toothpick.config.Module

internal class CoreModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(Gson::class.java).toInstance(Gson())
        val db = getDb(context)
        bind(FavoriteDao::class.java).toInstance(db.getFavoriteDao())
        bind(MovieDao::class.java).toInstance(db.getMovieDao())
        bind(PrivateDataSource::class.java).toInstance(PrivateDataSourceImpl(context))
        bind(Logger::class.java).toInstance(LoggerImpl())
        bind(ErrorMessageDataSource::class.java).toInstance(ErrorMessageDataSourceImpl(context))
        bind(HttpDataSource::class.java).toProvider(HttpDataSourceProvider::class.java)
                .providesSingleton()
        bind(Config::class.java).toInstance(Config(-1, 20))
    }

    private fun getDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.DB_NAME).build()
    }
}