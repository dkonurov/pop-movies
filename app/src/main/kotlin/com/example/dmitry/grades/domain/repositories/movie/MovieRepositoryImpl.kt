package com.example.dmitry.grades.domain.repositories.movie

import com.example.base.extensions.await
import com.example.base.schedulers.SchedulerProvider
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.db.inteface.MovieDao
import com.example.core.data.preferences.PrivateDataSource
import com.example.core.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class MovieRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val privateDataSource: PrivateDataSource,
    private val movieDao: MovieDao,
    private val favoriteDao: FavoriteDao,
    private val movieMapper: MovieMapper,
    private val schedulerProvider: SchedulerProvider
) : MovieRepository {

    companion object {
        internal const val PER_PAGE = 20
        const val UNKNOWN_COUNT_PAGE = -1
    }

    override suspend fun getMovies(
        page: Int,
        sortBy: String?
    ): MovieListInfo {

        val list = withContext(schedulerProvider.io()) {
            movieDao.getMovies((page - 1) * PER_PAGE, PER_PAGE)
        }
        return if (list.isEmpty()) {
            getRemoteMovies(page, sortBy)
        } else {
            movieMapper.toMovieListInfo(
                UNKNOWN_COUNT_PAGE, privateDataSource.baseUrlImg,
                privateDataSource.posterSize, list, page
            )
        }
    }

    private suspend fun getRemoteMovies(
        page: Int = 1,
        sortBy: String? = null
    ): MovieListInfo {

        val response = withContext(schedulerProvider.io()) {
            val response = httpDataSource.getListMovies(page, sortBy).await()
            movieDao.save(response.movies)
            return@withContext response
        }
        return movieMapper.toMovieListInfo(
            response.totalPages, privateDataSource.baseUrlImg,
            privateDataSource.posterSize, response.movies.toMutableList(), page
        )
    }

    override suspend fun findMovie(id: Long): ViewMovie {
        return withContext(schedulerProvider.io()) {
            val details = httpDataSource.getDetailsMovie(id).await()
            val isFavorite = favoriteDao.findById(id) != null
            movieMapper.toViewMovie(
                details, privateDataSource.baseUrlImg,
                privateDataSource.posterSize, isFavorite
            )
        }
    }

    override suspend fun clearCache() {
        runBlocking(schedulerProvider.io()) {
            movieDao.clear()
        }
    }

    override suspend fun saveFavorite(viewMovie: ViewMovie) {
        withContext(schedulerProvider.io()) {
            favoriteDao.save(movieMapper.toFavorite(viewMovie))
        }
    }

    override suspend fun removeFavorite(viewMovie: ViewMovie) {
        withContext(schedulerProvider.io()) {
            favoriteDao.delete(movieMapper.toFavorite(viewMovie))
        }
    }
}