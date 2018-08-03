package com.example.dmitry.grades.domain.mappers


import com.example.dmitry.grades.domain.models.Movie
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun toMovieListInfo(countPages: Int, pathImg: String?, sizeImg: String?, movies: MutableList<Movie>,
                        page: Int): MovieListInfo {
        movies.forEach {
            if (it.posterPath != null) {
                it.posterPath = pathImg + sizeImg + it.posterPath
            }
        }
        return MovieListInfo(countPages, movies, page)
    }
}