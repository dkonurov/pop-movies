package com.example.movie.domain.repositories

import com.example.core.network.model.MovieDTO
import com.example.core.storage.db.entity.LocalMovie
import javax.inject.Inject

internal class MovieMapper @Inject constructor() {

    fun mapFromDTOToLocal(movie: MovieDTO, page: Int) = LocalMovie(
        id = movie.id,
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
        voteAverage = movie.voteAverage,
        page = page
    )
}