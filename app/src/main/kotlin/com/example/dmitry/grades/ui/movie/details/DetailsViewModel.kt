package com.example.dmitry.grades.ui.movie.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.schedulers.SchedulerProvider
import com.example.base.ui.vm.ErrorViewModel
import com.example.core.data.logger.Logger
import com.example.core.data.message.ErrorMessageDataSource
import com.example.dmitry.grades.di.MovieId
import com.example.dmitry.grades.domain.models.PrimitiveWrapper
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    schedulerProvider: SchedulerProvider,
    errorMessageDataSource: ErrorMessageDataSource,
    logger: Logger,
    @MovieId private val wrapperId: PrimitiveWrapper<Long>,
    private val movieRepository: MovieRepository
) : ErrorViewModel(coroutineScope, schedulerProvider, errorMessageDataSource, logger) {

    private val _movie = MutableLiveData<ViewMovie>()

    val movie: LiveData<ViewMovie>
        get() = _movie

    fun load() {
        coroutine {
            _movie.value = movieRepository.findMovie(wrapperId.value)
        }
    }

    fun saveFavorite() {
        _movie.value?.let { movie ->
            coroutine {
                movieRepository.saveFavorite(movie)
                movie.isFavorite = true
                _movie.value = movie
            }
        }
    }

    fun removeFavorite() {
        _movie.value?.let { movie ->
            coroutine {
                movieRepository.removeFavorite(movie)
                movie.isFavorite = false
                _movie.value = movie
            }
        }
    }
}