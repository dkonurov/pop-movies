package com.example.dmitry.grades.di.providers

import com.example.dmitry.grades.domain.data.db.AppDatabase
import com.example.dmitry.grades.domain.data.db.FavoriteDao
import javax.inject.Inject
import javax.inject.Provider

class FavoriteDaoProvider @Inject constructor(private val appDatabase: AppDatabase) : Provider<FavoriteDao> {
    override fun get(): FavoriteDao {
        return appDatabase.getFavoriteDao()
    }
}