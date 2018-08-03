package com.example.dmitry.grades.domain.repositories

import com.example.dmitry.grades.domain.data.db.MovieDao
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import io.reactivex.Single
import javax.inject.Inject

open class HttpRepository @Inject constructor(private val httpDataSource: HttpDataSource,
                                              private val privateDataSource: PrivateDataSource,
                                              private val movieDao: MovieDao,
                                              private val movieMapper: MovieMapper) {

    companion object {
        private const val PER_PAGE = 20
        public const val UNKNOWN_COUNT_PAGE = -1
    }

    open fun getConfiguration(): Single<ImageConfig> {
        return httpDataSource.getConfiguration()
                .map {
                    privateDataSource.saveImageConfig(it.imageConfig)
                    return@map it.imageConfig
                }
    }

    open fun getMovies(page: Int = 1,
                       sortBy: String? = null): Single<MovieListInfo> {
        return movieDao.getMovies(0, page * PER_PAGE)
                .flatMap {
                    if (it.isEmpty()) {
                        getRemoteMovies(page, sortBy)
                    } else {
                        Single.just(movieMapper.toMovieListInfo(UNKNOWN_COUNT_PAGE, privateDataSource.baseUrlImg,
                                privateDataSource.posterSizes?.get(0), it))
                    }
                }
    }


    private fun getRemoteMovies(page: Int = 1,
                                sortBy: String? = null): Single<MovieListInfo> {
        return httpDataSource.getMovies(page, sortBy)
                .flatMap { discover ->
                    movieDao.save(discover.movies)
                    movieDao.getAll().map {
                        movieMapper.toMovieListInfo(discover.totalPages, privateDataSource.baseUrlImg,
                                privateDataSource.posterSizes?.get(0), it)
                    }
                }
    }

    open fun clearCache() {
        movieDao.clear()
    }
}