package com.example.details.domain.mappers

import com.example.core.network.model.DetailsMovieDTO
import com.example.core.storage.db.entity.LocalFavorite
import com.example.details.view.ViewMovie
import javax.inject.Inject

internal class MovieMapper @Inject constructor() {

    companion object {
        private const val MIN_YEAR_LENGTH = 4
    }

    fun toViewMovie(
        movie: DetailsMovieDTO,
        pathImg: String?,
        sizeImg: String?,
        isFavorite: Boolean
    ): ViewMovie {
        var poster: String? = null
        if (movie.posterPath != null && pathImg != null && sizeImg != null) {
            poster = pathImg + sizeImg + movie.posterPath
        }
        movie.posterPath = poster
        val length = movie.releaseDate.length
        val year = if (length < MIN_YEAR_LENGTH) {
            movie.releaseDate.substring(0, length)
        } else {
            movie.releaseDate.substring(
                0,
                MIN_YEAR_LENGTH
            )
        }
        return ViewMovie(
            movie.id, movie.title, movie.overview, movie.posterPath, year, movie.runtime,
            movie.releaseDate, isFavorite
        )
    }

    fun toFavorite(viewMovie: ViewMovie): LocalFavorite {
        return LocalFavorite(
            viewMovie.id,
            viewMovie.title,
            viewMovie.about,
            viewMovie.poster,
            viewMovie.time,
            viewMovie.release
        )
    }
}