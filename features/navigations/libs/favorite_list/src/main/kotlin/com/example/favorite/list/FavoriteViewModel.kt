package com.example.favorite.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.schedulers.DispatcherProvider
import com.example.base.ui.ui.errors.UiErrorMapper
import com.example.base.ui.vm.ErrorViewModel
import com.example.core.data.logger.Logger
import com.example.core.storage.db.entity.LocalMovie
import com.example.favorite.domain.usecase.FavoriteListUseCase
import com.example.favorite.list.view.FavoriteListRouter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class FavoriteViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    dispatcherProvider: DispatcherProvider,
    uiErrorMapper: UiErrorMapper,
    logger: Logger,
    private val favoriteListRouter: FavoriteListRouter,
    private val favoriteListUseCase: FavoriteListUseCase
) : ErrorViewModel(coroutineScope, dispatcherProvider, logger, uiErrorMapper) {

    private val _movies = MutableLiveData<MutableList<LocalMovie>>()

    private var page = 1

    private var countPage: Int? = null

    val movies: LiveData<MutableList<LocalMovie>>
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
            val movies = _movies.value ?: arrayListOf()
            movies.addAll(info.movies)
            _movies.value = movies
            countPage = info.countPage
            page = info.countPage
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

    fun showDetails(id: Long) {
        favoriteListRouter.showDetails(id)
    }
}