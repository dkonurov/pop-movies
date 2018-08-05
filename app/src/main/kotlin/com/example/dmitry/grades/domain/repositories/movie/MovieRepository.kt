package com.example.dmitry.grades.domain.repositories.movie

import com.example.dmitry.grades.domain.data.db.FavoriteDao
import com.example.dmitry.grades.domain.data.db.MovieDao
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class MovieRepository @Inject constructor(private val httpDataSource: HttpDataSource,
                                               private val privateDataSource: PrivateDataSource,
                                               private val movieDao: MovieDao,
                                               private val favoriteDao: FavoriteDao,
                                               private val movieMapper: MovieMapper) {

    companion object {
        internal const val PER_PAGE = 20
        const val UNKNOWN_COUNT_PAGE = -1
    }

    open fun getMovies(page: Int = 1,
                       sortBy: String? = null): Single<MovieListInfo> {
        return movieDao.getMovies((page - 1) * PER_PAGE, PER_PAGE)
                .flatMap {
                    if (it.isEmpty()) {
                        getRemoteMovies(page, sortBy)
                    } else {
                        Single.just(movieMapper.toMovieListInfo(UNKNOWN_COUNT_PAGE, privateDataSource.baseUrlImg,
                                getPosterSize(), it, page))
                    }
                }
    }

    private fun getPosterSize(): String? {
        val size = privateDataSource.posterSizes
        return if (size == null || size.isEmpty()) {
            null
        } else {
            size[0]
        }
    }


    private fun getRemoteMovies(page: Int = 1,
                                sortBy: String? = null): Single<MovieListInfo> {
        return httpDataSource.getListMovies(page, sortBy)
                .flatMap { discover ->
                    movieDao.save(discover.movies)
                    movieDao.getMovies((page - 1) * PER_PAGE, PER_PAGE).map {
                        movieMapper.toMovieListInfo(discover.totalPages, privateDataSource.baseUrlImg,
                                getPosterSize(), it, page)
                    }
                }
    }

    fun findMovie(id: Long): Single<ViewMovie> {
        return httpDataSource.getDetailsMovie(id)
                .map {
                    val isFavorite = favoriteDao.findById(id) != null
                    movieMapper.toViewMovie(it, privateDataSource.baseUrlImg, getPosterSize(), isFavorite)
                }
    }

    open fun clearCache() {
        movieDao.clear()
    }

    open fun saveFavorite(viewMovie: ViewMovie) {
        favoriteDao.save(movieMapper.toFavorite(viewMovie))
    }

    open fun removeFavorite(viewMovie: ViewMovie) {
        favoriteDao.deleteById(viewMovie.id)
    }

    open fun getFavorites(page: Int = 1): Single<MovieListInfo> {
        return favoriteDao.count()
                .flatMap { count ->
                    val countPages = count / PER_PAGE
                    favoriteDao.getMoviesId((page - 1) * PER_PAGE, PER_PAGE)
                            .flatMap {
                                Flowable.fromIterable(it)
                                        .flatMap { id ->
                                            httpDataSource.getMovie(id).toFlowable()
                                        }
                                        .toList()
                            }
                            .map { movies ->
                                movieMapper.toMovieListInfo(countPages, privateDataSource.baseUrlImg,
                                        getPosterSize(), movies, page)
                            }
                }
    }
}