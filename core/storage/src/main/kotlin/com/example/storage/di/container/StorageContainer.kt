package com.example.storage.di.container

import android.content.Context
import com.example.storage.config.Config
import com.example.storage.db.inteface.FavoriteDao
import com.example.storage.db.inteface.MovieDao
import com.example.storage.preferences.ErrorMessageDataSource
import com.example.storage.preferences.PrivateDataSource

interface StorageContainer {
    fun getFavoriteDao(): FavoriteDao
    fun getMovieDao(): MovieDao
    fun getPrivateDataSource(): PrivateDataSource
    fun getErrorMessageDataSource(): ErrorMessageDataSource
    fun getConfig(): Config
}

object StorageContainerFactory {
    fun create(context: Context): StorageContainer {
        return DaggerStorageContainerImpl.factory().create(context)
    }
}