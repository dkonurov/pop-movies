package com.example.dmitry.grades.ui.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.models.entity.Movie
import com.example.dmitry.grades.domain.Logger
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.repositories.favorite.FavoriteRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieConfigRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.ui.base.vm.ErrorViewModel
import com.example.dmitry.grades.ui.movie.list.FilterType
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    schedulerProvider: SchedulerProvider,
    resourceRepository: ResourceRepository,
    logger: Logger,
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
    private val movieConfigRepository: MovieConfigRepository
) : ErrorViewModel(coroutineScope, schedulerProvider, resourceRepository, logger) {

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
            val info = favoriteRepository.getFavorites(page = page)
            val movies = _movies.value
            if (movies == null) {
                _movies.value = info.movies
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