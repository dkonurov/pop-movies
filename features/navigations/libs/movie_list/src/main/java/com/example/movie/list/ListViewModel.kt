package com.example.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.schedulers.SchedulerProvider
import com.example.base.ui.vm.ErrorViewModel
import com.example.bottom.navigation.domain.config.SortConfigRepository
import com.example.bottom.navigation.ui.models.FilterType
import com.example.core.data.logger.Logger
import com.example.movie.domain.usecase.MovieListUseCase
import com.example.movie.list.view.MovieListRouter
import com.example.storage.config.Config
import com.example.storage.db.entity.LocalMovie
import com.example.storage.preferences.ErrorMessageDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class ListViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    schedulerProvider: SchedulerProvider,
    errorMessageDataSource: ErrorMessageDataSource,
    logger: Logger,
    private val movieListRouter: MovieListRouter,
    private val movieListUseCase: MovieListUseCase,
    private val sortConfigRepository: SortConfigRepository,
    private val config: Config
) : ErrorViewModel(coroutineScope, schedulerProvider, errorMessageDataSource, logger) {

    private val _movies = MutableLiveData<MutableList<LocalMovie>>()

    private var page = 1

    private var countPage: Int? = null

    private var _sortyBy: String? = sortConfigRepository.sortBy

    private var moreMovies = false

    val movies: LiveData<MutableList<LocalMovie>>
        get() = _movies

    override fun showLoading() {
        if (page == 1) {
            super.showLoading()
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        moreMovies = false
    }

    fun load() {
        coroutine {
            val info = movieListUseCase.getMovies(page, _sortyBy)
            val movies = info.movies
            val old = _movies.value
            if (old == null) {
                _movies.value = movies.toMutableList()
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
            movieListUseCase.clearCache()
            _movies.value = null
            page = 1
            load()
        }
    }

    fun filter(filterType: FilterType) {
        val text = when (filterType) {
            FilterType.RELEASE_DATE -> FilterType.RELEASE_DATE.text
            FilterType.VOTE_COUNT -> FilterType.VOTE_COUNT.text
            else -> FilterType.POPULARITY.text
        }
        if (text != _sortyBy) {
            sortConfigRepository.sortBy = text
            _sortyBy = text
            forceLoad()
        }
    }

    fun loadMore() {
        countPage?.let {
            if (!moreMovies && (it > page || it == config.unknownCountPage)) {
                moreMovies = true
                page++
                load()
            }
        }
    }

    fun showDetails(movieId: Long) {
        movieListRouter.showDetails(movieId)
    }
}