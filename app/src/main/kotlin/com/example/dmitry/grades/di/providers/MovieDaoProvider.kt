package com.example.dmitry.grades.di.providers

import com.example.dmitry.grades.domain.data.db.AppDatabase
import com.example.dmitry.grades.domain.data.db.MovieDao
import javax.inject.Inject
import javax.inject.Provider

class MovieDaoProvider @Inject constructor(private val appDatabase: AppDatabase) : Provider<MovieDao> {
    override fun get(): MovieDao {
        return appDatabase.getMovieDao()
    }
}