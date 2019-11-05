package com.example.details.view

internal data class ViewMovie(
    val id: Long,
    val title: String,
    val about: String,
    val poster: String?,
    val year: String,
    val time: String?,
    val release: String,
    var isFavorite: Boolean
)