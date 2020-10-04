package com.example.grid

import com.example.core.models.entity.Movie

fun interface MovieClickListener {
    fun onClick(movie: Movie)
}