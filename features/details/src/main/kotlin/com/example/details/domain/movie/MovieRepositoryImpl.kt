package com.example.details.domain.movie

import com.example.base.extensions.await
import com.example.base.schedulers.SchedulerProvider
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.preferences.PrivateDataSource
import com.example.core.data.remote.HttpDataSource
import com.example.details.domain.mappers.MovieMapper
import com.example.details.view.ViewMovie
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val privateDataSource: PrivateDataSource,
    private val favoriteDao: FavoriteDao,
    private val movieMapper: MovieMapper,
    private val schedulerProvider: SchedulerProvider
) : MovieRepository {

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