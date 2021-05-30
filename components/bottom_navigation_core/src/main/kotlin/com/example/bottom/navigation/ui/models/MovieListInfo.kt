package com.example.bottom.navigation.ui.models

import com.example.storage.db.entity.LocalMovie

data class MovieListInfo(val countPage: Int, val movies: List<LocalMovie>, val page: Int)