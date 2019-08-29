package com.example.dmitry.grades.domain.repositories.favorite

import com.example.base.extensions.await
import com.example.base.schedulers.SchedulerProvider
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.preferences.PrivateDataSource
import com.example.core.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val movieMapper: MovieMapper,
    private val httpDataSource: HttpDataSource,
    private val privateDataSource: PrivateDataSource,
    private val schedulerProvider: SchedulerProvider
) : FavoriteRepository {

    override suspend fun saveFavorite(viewMovie: ViewMovie) {
        withContext(schedulerProvider.io()) {
            favoriteDao.save(movieMapper.toFavorite(viewMovie))
        }
    }

    override suspend fun removeFavorite(viewMovie: ViewMovie) {
        withContext(schedulerProvider.io()) {
            favoriteDao.deleteById(viewMovie.id)
        }
    }

    override suspend fun getFavorites(page: Int): MovieListInfo {
        return withContext(schedulerProvider.io()) {
            val count = favoriteDao.count()
            val countPages = count / MovieRepositoryImpl.PER_PAGE
            val movies = favoriteDao.getMoviesId(
                (page - 1) * MovieRepositoryImpl.PER_PAGE,
                MovieRepositoryImpl.PER_PAGE
            )
            val remote = movies.map {
                httpDataSource.getMovie(it).await()
            }.toMutableList()
            movieMapper.toMovieListInfo(
                countPages, privateDataSource.baseUrlImg,
                privateDataSource.posterSize, remote, page
            )
        }
    }
}