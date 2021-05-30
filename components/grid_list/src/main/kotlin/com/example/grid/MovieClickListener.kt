package com.example.grid

import com.example.storage.db.entity.LocalMovie


fun interface MovieClickListener {
    fun onClick(movie: LocalMovie)
}