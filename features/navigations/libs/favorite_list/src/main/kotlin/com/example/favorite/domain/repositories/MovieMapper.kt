package com.example.favorite.domain.repositories

import com.example.core.network.models.MovieDTO
import com.example.core.storage.db.entity.LocalMovie
import javax.inject.Inject

internal class MovieMapper @Inject constructor() {

    fun mapFromDTOToLocal(movie: MovieDTO) = LocalMovie(
        id = movie.id,
        localId = 0,
        posterPath = movie.posterPath,
        adult = movie.adult,
        overview = movie.overview,
        releaseDate = movie.releaseDate,
        genreIds = movie.genreIds,
        originalTitle = movie.originalTitle,
        originalLanguage = movie.originalLanguage,
        title = movie.title,
        backdropPath = movie.backdropPath,
        popularity = movie.popularity,
        voteCount = movie.voteCount,
        isVideo = movie.isVideo,
        voteAverage = movie.voteAverage
    )
}