package com.example.movie.domain.repositories

import com.example.base.schedulers.SchedulerProvider
import com.example.bottom.navigation.domain.models.MovieResponse
import com.example.core.data.config.Config
import com.example.core.data.db.inteface.MovieDao
import com.example.core.data.remote.HttpDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieListRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val movieDao: MovieDao,
    private val schedulerProvider: SchedulerProvider,
    private val config: Config
) : MovieListRepository {

    override suspend fun getMovies(
        page: Int,
        sortBy: String?
    ): MovieResponse {

        val list = withContext(schedulerProvider.io()) {
            movieDao.getMovies((page - 1) * config.perPage, config.perPage)
        }
        return if (list.isEmpty()) {
            getRemoteMovies(page, sortBy)
        } else {
            return MovieResponse(config.unknownCountPage, list)
        }
    }

    private suspend fun getRemoteMovies(
        page: Int = 1,
        sortBy: String? = null
    ): MovieResponse = withContext(schedulerProvider.io()) {
        val response = httpDataSource.getListMovies(page, sortBy)
        movieDao.save(response.movies)
        MovieResponse(response.totalPages, response.movies)
    }

    override suspend fun clearCache() = withContext(schedulerProvider.io()) {
        movieDao.clear()
    }
}