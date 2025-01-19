package com.example.details.domain.movie

import com.example.base.schedulers.DispatcherProvider
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.db.inteface.FavoriteDao
import com.example.core.storage.preferences.PrivateDataSource
import com.example.details.domain.mappers.MovieMapper
import com.example.details.view.ViewMovie
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val privateDataSource: PrivateDataSource,
    private val favoriteDao: FavoriteDao,
    private val movieMapper: MovieMapper,
    private val dispatcherProvider: DispatcherProvider
) : MovieRepository {

    override suspend fun findMovie(id: Long): ViewMovie {
        return withContext(dispatcherProvider.io()) {
            val details = httpDataSource.getDetailsMovie(id)
            val isFavorite = favoriteDao.findById(id) != null
            movieMapper.toViewMovie(
                details, privateDataSource.baseUrlImg,
                privateDataSource.posterSize, isFavorite
            )
        }
    }

    override suspend fun saveFavorite(viewMovie: ViewMovie) {
        withContext(dispatcherProvider.io()) {
            favoriteDao.save(movieMapper.toFavorite(viewMovie))
        }
    }

    override suspend fun removeFavorite(viewMovie: ViewMovie) {
        withContext(dispatcherProvider.io()) {
            favoriteDao.delete(movieMapper.toFavorite(viewMovie))
        }
    }
}