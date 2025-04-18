package com.example.movie.domain.repositories

import com.example.bottom.navigation.domain.models.MoviePage
import com.example.core.coroutine.resultOf
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.config.Config
import com.example.core.storage.db.inteface.MovieDao
import javax.inject.Inject

internal class MovieListRepository
    @Inject
    constructor(
        private val httpDataSource: HttpDataSource,
        private val movieDao: MovieDao,
        private val mapper: MovieMapper,
        private val config: Config,
    ) {
        suspend fun getMovies(
            page: Int,
            sortBy: String?,
        ): MoviePage =
            resultOf {
                getRemoteMovies(page, sortBy)
            }.getOrElse {
                if (sortBy !== null) {
                    throw it
                }
                val movies = movieDao.getMovies(page)
                if (movies.isEmpty()) {
                    throw it
                }
                MoviePage(this.config.unknownCountPage, movies)
            }

        private suspend fun getRemoteMovies(
            page: Int,
            sortBy: String?,
        ): MoviePage {
            val response = httpDataSource.getListMovies(page, sortBy)
            val mapped = response.movies.map { mapper.mapFromDTOToLocal(it, page) }
            if (page == 1 && sortBy == null) {
                resultOf { movieDao.clear() }
            }
            movieDao.save(mapped)
            return MoviePage(response.totalPages, mapped)
        }
    }