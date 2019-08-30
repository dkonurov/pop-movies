package com.example.favorite.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.schedulers.SchedulerProvider
import com.example.base.ui.vm.ErrorViewModel
import com.example.core.data.logger.Logger
import com.example.core.data.message.ErrorMessageDataSource
import com.example.core.models.entity.Movie
import com.example.favorite.domain.usecase.FavoriteListUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class FavoriteViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    schedulerProvider: SchedulerProvider,
    errorMessageDataSource: ErrorMessageDataSource,
    logger: Logger,
    private val favoriteListUseCase: FavoriteListUseCase
) : ErrorViewModel(coroutineScope, schedulerProvider, errorMessageDataSource, logger) {

    private val _movies = MutableLiveData<MutableList<Movie>>()

    private var page = 1

    private var countPage: Int? = null

    val movies: LiveData<MutableList<Movie>>
        get() = _movies

    private var moreMovies = false

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
            val info = favoriteListUseCase.getFavorites(page)
            val movies = _movies.value
            if (movies == null) {
                _movies.value = info.movies.toMutableList()
            } else {
                movies.addAll(info.movies)
                _movies.value = movies
            }
            countPage = info.countPage
            page = info.page
        }
    }

    fun forceLoad() {
        coroutine {
            _movies.value = null
            page = 1
            load()
        }
    }

    fun loadMore() {
        countPage?.let {
            if (!moreMovies && (it > page)) {
                moreMovies
                page++
                load()
            }
        }
    }
}