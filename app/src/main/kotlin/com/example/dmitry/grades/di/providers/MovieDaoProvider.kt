package com.example.dmitry.grades.di.providers

import com.example.core.data.db.inteface.IDatabase
import com.example.core.data.db.inteface.MovieDao
import javax.inject.Inject
import javax.inject.Provider

class MovieDaoProvider @Inject constructor(private val db: IDatabase) : Provider<MovieDao> {
    override fun get(): MovieDao {
        return db.getMovieDao()
    }
}