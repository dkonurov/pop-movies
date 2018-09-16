package com.example.dmitry.grades.ui.movie.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dmitry.grades.di.MovieId
import com.example.dmitry.grades.domain.Logger
import com.example.dmitry.grades.domain.models.PrimitiveWrapper
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.repositories.favorite.FavoriteRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.ui.base.vm.ErrorViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor(@MovieId private val wrapperId: PrimitiveWrapper<Long>,
                                           private val movieRepository: MovieRepository,
                                           private val favoriteRepository: FavoriteRepository,
                                           resourceRepository: ResourceRepository,
                                           logger: Logger) : ErrorViewModel(resourceRepository, logger) {

    private val _movie = MutableLiveData<ViewMovie>()

    val movie: LiveData<ViewMovie>
        get() = _movie

    fun load() {
        coroutine {
            _movie.value = movieRepository.findMovie(wrapperId.value)
        }
    }

    fun saveFavorite() {
        _movie.value?.let {
            favoriteRepository.saveFavorite(it)
            it.isFavorite = true
            _movie.value = it
        }

    }

    fun removeFavorite() {
        _movie.value?.let {
            favoriteRepository.removeFavorite(it)
            it.isFavorite = false
            _movie.value = it
        }

    }
}