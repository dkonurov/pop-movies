package com.example.grid

import com.example.core.storage.db.entity.LocalMovie

fun interface MovieClickListener {
    fun onClick(movie: LocalMovie)
}