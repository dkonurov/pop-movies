package com.example.dmitry.grades.ui.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.schedulers.SchedulerProvider
import com.example.base.ui.vm.ErrorViewModel
import com.example.core.data.logger.Logger
import com.example.core.data.message.ErrorMessageDataSource
import com.example.core.models.entity.Movie
import com.example.dmitry.grades.domain.repositories.movie.MovieConfigRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ListViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    schedulerProvider: SchedulerProvider,
    errorMessageDataSource: ErrorMessageDataSource,
    logger: Logger,
    private val movieRepository: MovieRepository,
    private val movieConfigRepository: MovieConfigRepository
) : ErrorViewModel(coroutineScope, schedulerProvider, errorMessageDataSource, logger) {

    private val _movies = MutableLiveData<MutableList<Movie>>()

    private var page = 1

    private var countPage: Int? = null

    private var _moreMovies = MutableLiveData<Boolean>()

    private var _sortyBy: String? = movieConfigRepository.sortBy

    val movies: LiveData<MutableList<Movie>>
        get() = _movies

    val moreMovies: LiveData<Boolean>
        get() = _moreMovies

    override fun showLoading() {
        if (page > 1) {
            _moreMovies.value = true
        } else {
            super.showLoading()
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        _moreMovies.value = false
    }

    fun load() {
        coroutine {
            val info = movieRepository.getMovies(page, _sortyBy)
            val movies = info.movies
            val old = _movies.value
            if (old == null) {
                _movies.value = movies
            } else {
                old.addAll(movies)
                _movies.value = old
            }
            countPage = info.countPage
            page = info.page
        }
    }

    fun forceLoad() {
        coroutine {
            movieRepository.clearCache()
            _movies.value = null
            page = 1
            load()
        }
    }

    fun filter(filterType: FilterType) {
        val text = when (filterType) {
            FilterType.RELEASE_DATE -> FilterType.RELEASE_DATE.text()
            FilterType.VOTE_COUNT -> FilterType.VOTE_COUNT.text()
            else -> FilterType.POPULARITY.text()
        }
        if (text != _sortyBy) {
            movieConfigRepository.sortBy = text
            _sortyBy = text
            forceLoad()
        }
    }

    fun loadMore() {
        countPage?.let {
            if (_moreMovies.value == false && (it > page || it == MovieRepositoryImpl.UNKNOWN_COUNT_PAGE)) {
                _moreMovies.value = true
                page++
                load()
            }
        }
    }
}