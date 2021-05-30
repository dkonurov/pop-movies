package com.example.storage.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.storage.config.Config
import com.example.storage.db.AppDatabase
import com.example.storage.db.inteface.FavoriteDao
import com.example.storage.db.inteface.MovieDao
import com.example.storage.di.scope.StorageScope
import com.example.storage.preferences.ErrorMessageDataSource
import com.example.storage.preferences.ErrorMessageDataSourceImpl
import com.example.storage.preferences.PrivateDataSource
import com.example.storage.preferences.PrivateDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal abstract class StorageModule {

    companion object {
        private const val SHARED_NAME = "com.example.dmitry.grades.domain.data.preferences.shared_name"
        private const val DB_NAME = "app-database"

        @Provides
        @StorageScope
        fun provideDb(context: Context): AppDatabase = getDb(context)

        @Provides
        fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.getFavoriteDao()

        @Provides
        fun provideMovieDao(db: AppDatabase): MovieDao = db.getMovieDao()

        @Provides
        @StorageScope
        fun provideConfig() = Config(-1, 20)

        @Provides
        @StorageScope
        fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
            SHARED_NAME,
            Context.MODE_PRIVATE
        )

        private fun getDb(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()
        }
    }

    @Binds
    @StorageScope
    abstract fun bindPrivateDataSource(impl: PrivateDataSourceImpl): PrivateDataSource

    @Binds
    @StorageScope
    abstract fun bindErrorMessageDataSource(impl: ErrorMessageDataSourceImpl): ErrorMessageDataSource
}