package com.example.dmitry.grades.domain.mappers


import com.example.core.models.entity.Favorite
import com.example.core.models.entity.Movie
import com.example.core.models.DetailsMovie
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import javax.inject.Inject

open class MovieMapper @Inject constructor() {

    companion object {
        private const val MIN_YEAR_LENGTH = 4
    }

    open fun toMovieListInfo(countPages: Int, pathImg: String?, sizeImg: String?, movies: MutableList<Movie>,
                             page: Int): MovieListInfo {
        movies.forEach {
            if (it.posterPath == null || pathImg == null || sizeImg == null) {
                it.posterPath = null
            } else {
                it.posterPath = pathImg + sizeImg + it.posterPath
            }
        }
        return MovieListInfo(countPages, movies, page)
    }

    open fun toViewMovie(movie: DetailsMovie, pathImg: String?, sizeImg: String?,
                         isFavorite: Boolean): ViewMovie {
        var poster: String? = null
        if (movie.posterPath != null && pathImg != null && sizeImg != null) {
            poster = pathImg + sizeImg + movie.posterPath
        }
        movie.posterPath = poster
        val length = movie.releaseDate.length
        val year = if (length < MIN_YEAR_LENGTH) {
            movie.releaseDate.substring(0, length)
        } else {
            movie.releaseDate.substring(0, MIN_YEAR_LENGTH)
        }
        return ViewMovie(movie.id, movie.title, movie.overview, movie.posterPath, year, movie.runtime,
                movie.releaseDate, isFavorite)
    }

    open fun toFavorite(viewMovie: ViewMovie): Favorite {
        return Favorite(viewMovie.id, viewMovie.title, viewMovie.about, viewMovie.poster, viewMovie.time, viewMovie.release)
    }
}