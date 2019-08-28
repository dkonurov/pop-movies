package com.example.dmitry.grades.di.providers

import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.db.inteface.IDatabase
import javax.inject.Inject
import javax.inject.Provider

class FavoriteDaoProvider @Inject constructor(private val db: IDatabase) : Provider<FavoriteDao> {
    override fun get(): FavoriteDao {
        return db.getFavoriteDao()
    }
}
