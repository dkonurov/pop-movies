package com.example.dmitry.grades.domain.repositories.favorite

import com.example.dmitry.grades.domain.data.db.FavoriteDao
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.models.entity.Movie
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val favoriteDao: FavoriteDao,
                                                 private val movieMapper: MovieMapper,
                                                 private val httpDataSource: HttpDataSource,
                                                 private val privateDataSource: PrivateDataSource) : FavoriteRepository {

    override fun saveFavorite(viewMovie: ViewMovie) {
        favoriteDao.save(movieMapper.toFavorite(viewMovie))
    }

    override fun removeFavorite(viewMovie: ViewMovie) {
        favoriteDao.deleteById(viewMovie.id)
    }

    override fun getFavorites(page: Int): Single<MovieListInfo> {
        return favoriteDao.count()
                .flatMap { count ->
                    val countPages = count / MovieRepositoryImpl.PER_PAGE
                    favoriteDao.getMoviesId((page - 1) * MovieRepositoryImpl.PER_PAGE, MovieRepositoryImpl.PER_PAGE)
                            .flatMap(this::findMoviesById)
                            .map {
                                movieMapper.toMovieListInfo(countPages, privateDataSource.baseUrlImg,
                                        privateDataSource.posterSize, it, page)
                            }
                }
    }

    private fun findMoviesById(movies: List<Long>): Single<MutableList<Movie>>? {
        return Flowable.fromIterable(movies)
                .flatMap {
                    httpDataSource.getMovie(it).toFlowable()
                }
                .toList()
    }
}