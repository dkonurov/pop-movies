package com.example.favorite.domain.repositories

import com.example.base.schedulers.DispatcherProvider
import com.example.bottom.navigation.domain.models.MoviePage
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.config.Config
import com.example.core.storage.db.inteface.FavoriteDao
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FavoriteListRepositoryImpl
    @Inject
    constructor(
        private val favoriteDao: FavoriteDao,
        private val httpDataSource: HttpDataSource,
        private val dispatcherProvider: DispatcherProvider,
        private val movieMapper: MovieMapper,
        private val config: Config,
    ) : FavoriteListRepository {
        override suspend fun getFavorites(page: Int): MoviePage =
            withContext(dispatcherProvider.io()) {
                val count = favoriteDao.count()
                val countPages = count / config.perPage
                val movieIds =
                    favoriteDao.getMoviesId(
                        (page - 1) * config.perPage,
                        config.perPage,
                    )
                val requests =
                    movieIds
                        .asSequence()
                        .map { id ->
                            async(dispatcherProvider.io()) {
                                httpDataSource.getMovie(id)
                            }
                        }.toList()
                val movies = requests.awaitAll().map { movieMapper.mapFromDTOToLocal(it, page) }
                MoviePage(countPages, movies)
            }
    }