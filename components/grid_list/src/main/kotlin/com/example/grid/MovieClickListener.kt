package com.example.grid

import com.example.core.models.entity.LocalMovie

fun interface MovieClickListener {
    fun onClick(movie: LocalMovie)
}