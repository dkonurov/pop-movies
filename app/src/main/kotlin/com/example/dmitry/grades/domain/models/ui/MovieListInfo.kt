package com.example.dmitry.grades.domain.models.ui

import com.example.dmitry.grades.domain.models.Movie

data class MovieListInfo(val countPage: Int, val movies: MutableList<Movie>, val page: Int)