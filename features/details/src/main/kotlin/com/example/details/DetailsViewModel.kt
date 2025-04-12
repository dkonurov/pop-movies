package com.example.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.schedulers.DispatcherProvider
import com.example.base.ui.ui.errors.UiErrorMapper
import com.example.base.ui.vm.ErrorViewModel
import com.example.core.data.logger.Logger
import com.example.core.storage.preferences.ErrorMessageDataSource
import com.example.details.di.MovieId
import com.example.details.domain.PrimitiveWrapper
import com.example.details.domain.movie.MovieRepository
import com.example.details.view.ViewMovie
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class DetailsViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    schedulerProvider: DispatcherProvider,
    uiErrorMapper: UiErrorMapper,
    logger: Logger,
    @MovieId private val wrapperId: PrimitiveWrapper<Long>,
    private val movieRepository: MovieRepository
) : ErrorViewModel(coroutineScope, schedulerProvider, logger, uiErrorMapper) {

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