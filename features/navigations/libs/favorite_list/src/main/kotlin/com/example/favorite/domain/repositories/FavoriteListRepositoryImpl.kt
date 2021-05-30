package com.example.favorite.domain.repositories

import com.example.base.schedulers.SchedulerProvider
import com.example.bottom.navigation.domain.models.MovieResponse
import com.example.core.data.config.Config
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.network.remote.HttpDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FavoriteListRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val httpDataSource: HttpDataSource,
    private val schedulerProvider: SchedulerProvider,
    private val movieMapper: MovieMapper,
    private val config: Config
) : FavoriteListRepository {

    override suspend fun getFavorites(page: Int): MovieResponse = withContext(schedulerProvider.io()) {
        val count = favoriteDao.count()
        val countPages = count / config.perPage
        val movieIds = favoriteDao.getMoviesId(
            (page - 1) * config.perPage,
            config.perPage
        )
        val requests = movieIds.asSequence().map { id ->
            async(schedulerProvider.io()) {
                httpDataSource.getMovie(id)
            }
        }.toList()
        val movies = requests.awaitAll().map { movieMapper.mapFromDTOToLocal(it) }
        MovieResponse(countPages, movies)
    }
}