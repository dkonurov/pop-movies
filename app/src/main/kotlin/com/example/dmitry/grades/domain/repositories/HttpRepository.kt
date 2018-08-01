package com.example.dmitry.grades.domain.repositories

import com.example.dmitry.grades.domain.data.db.MovieDao
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import io.reactivex.Single
import javax.inject.Inject

open class HttpRepository @Inject constructor(private val httpDataSource: HttpDataSource,
                                              private val privateDataSource: PrivateDataSource,
                                              private val movieDao: MovieDao) {

    open fun getConfiguration(): Single<ImageConfig> {
        return httpDataSource.getConfiguration()
                .map {
                    privateDataSource.saveImageConfig(it.imageConfig)
                    return@map it.imageConfig
                }
    }

    open fun getMovies(page: Int? = null,
                       sortBy: String? = null,
                       year: Int? = null): Single<MovieListInfo> {
        return httpDataSource.getMovies()
                .flatMap { discover ->
                    movieDao.save(discover.movies)
                    movieDao.getAll().map {
                        MovieListInfo(discover.totalPages, it)
                    }
                }
    }
}