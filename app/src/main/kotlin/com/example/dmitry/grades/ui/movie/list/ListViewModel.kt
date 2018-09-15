package com.example.dmitry.grades.ui.movie.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dmitry.grades.domain.Logger
import com.example.dmitry.grades.domain.models.entity.Movie
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieConfigRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import com.example.dmitry.grades.ui.base.vm.ErrorViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ListViewModel @Inject constructor(private val movieRepository: MovieRepository,
                                        resourceRepository: ResourceRepository,
                                        logger: Logger,
                                        private val movieConfigRepository: MovieConfigRepository) : ErrorViewModel(resourceRepository, logger) {

    private val _movies = MutableLiveData<MutableList<Movie>>()

    private var page = 1

    private var countPage: Int? = null

    private var _moreMovies = MutableLiveData<Boolean>()

    private var _sortyBy: String? = movieConfigRepository.sortBy

    private var _disposable: Disposable? = null

    val movies: LiveData<MutableList<Movie>>
        get() = _movies

    val moreMovies: LiveData<Boolean>
        get() = _moreMovies

    fun load() {
        _disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        coroutine {
            if (page > 1) {
                _moreMovies.value = true
            }
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
            _moreMovies.value = false
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

    override fun onCleared() {
        super.onCleared()
        _disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
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