package com.example.movie.domain.repositories

import com.example.base.schedulers.DispatcherProvider
import com.example.bottom.navigation.domain.models.MoviePage
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.config.Config
import com.example.core.storage.db.inteface.MovieDao
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieListRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val movieDao: MovieDao,
    private val schedulerProvider: DispatcherProvider,
    private val mapper: MovieMapper,
    private val config: Config
) : MovieListRepository {

    override suspend fun getMovies(
        page: Int,
        sortBy: String?
    ): MoviePage {

        val list = withContext(schedulerProvider.io()) {
            movieDao.getMovies((page - 1) * config.perPage, config.perPage)
        }
        return if (list.isEmpty()) {
            getRemoteMovies(page, sortBy)
        } else {
            return MoviePage(config.unknownCountPage, list)
        }
    }

    private suspend fun getRemoteMovies(
        page: Int = 1,
        sortBy: String? = null
    ): MoviePage = withContext(schedulerProvider.io()) {
        val response = httpDataSource.getListMovies(page, sortBy)
        val mapped = response.movies.map { mapper.mapFromDTOToLocal(it) }
        movieDao.save(mapped)
        MoviePage(response.totalPages, mapped)
    }

    override suspend fun clearCache() = withContext(schedulerProvider.io()) {
        movieDao.clear()
    }
}