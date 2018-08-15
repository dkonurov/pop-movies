package com.example.dmitry.grades.domain.repositories.movie

import com.example.dmitry.grades.domain.data.db.FavoriteDao
import com.example.dmitry.grades.domain.data.db.MovieDao
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import io.reactivex.Single
import javax.inject.Inject

open class MovieRepositoryImpl @Inject constructor(private val httpDataSource: HttpDataSource,
                                                   private val privateDataSource: PrivateDataSource,
                                                   private val movieDao: MovieDao,
                                                   private val favoriteDao: FavoriteDao,
                                                   private val movieMapper: MovieMapper) : MovieRepository {

    companion object {
        internal const val PER_PAGE = 20
        const val UNKNOWN_COUNT_PAGE = -1
    }

    override fun getMovies(page: Int,
                           sortBy: String?): Single<MovieListInfo> {
        return movieDao.getMovies((page - 1) * PER_PAGE, PER_PAGE)
                .flatMap {
                    if (it.isEmpty()) {
                        getRemoteMovies(page, sortBy)
                    } else {
                        Single.just(movieMapper.toMovieListInfo(UNKNOWN_COUNT_PAGE, privateDataSource.baseUrlImg,
                                privateDataSource.posterSize, it, page))
                    }
                }
    }


    private fun getRemoteMovies(page: Int = 1,
                                sortBy: String? = null): Single<MovieListInfo> {
        return httpDataSource.getListMovies(page, sortBy)
                .doOnSuccess {
                    movieDao.save(it.movies)
                }
                .map {
                    movieMapper.toMovieListInfo(it.totalPages, privateDataSource.baseUrlImg,
                            privateDataSource.posterSize, it.movies.toMutableList(), page)
                }
    }

    override fun findMovie(id: Long): Single<ViewMovie> {
        return httpDataSource.getDetailsMovie(id)
                .map {
                    val isFavorite = favoriteDao.findById(id) != null
                    movieMapper.toViewMovie(it, privateDataSource.baseUrlImg, privateDataSource.posterSize, isFavorite)
                }
    }

    override fun clearCache() {
        movieDao.clear()
    }
}