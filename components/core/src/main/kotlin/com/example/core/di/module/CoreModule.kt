package com.example.core.di.module

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
import com.example.core.di.scope.CoreScope
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal abstract class CoreModule(context: Context) {

    companion object {
        @Provides
        @CoreScope
        fun provideGson(): Gson = Gson()

        @Provides
        @CoreScope
        fun provideDb(context: Context): AppDatabase = getDb(context)

        @Provides
        fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.getFavoriteDao()

        @Provides
        fun provideMovieDao(db: AppDatabase): MovieDao = db.getMovieDao()

        @Provides
        @CoreScope
        fun provideConfig() = Config(-1, 20)

        private fun getDb(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, BuildConfig.DB_NAME)
                .build()
        }
    }

    @Binds
    @CoreScope
    abstract fun bindPrivateDataSource(impl: PrivateDataSourceImpl): PrivateDataSource

    @Binds
    @CoreScope
    abstract fun bindLogger(impl: LoggerImpl): Logger

    @Binds
    @CoreScope
    abstract fun bindErrorMessageDataSource(impl: ErrorMessageDataSourceImpl): ErrorMessageDataSource
}