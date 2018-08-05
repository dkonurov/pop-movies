package com.example.dmitry.grades.ui.movie.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dmitry.grades.domain.Logger
import com.example.dmitry.grades.domain.models.entity.Movie
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieConfigRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.ui.base.BaseViewModel
import com.example.dmitry.grades.ui.base.async
import com.example.dmitry.grades.ui.base.loading
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ListViewModel @Inject constructor(private val movieRepository: MovieRepository,
                                        private val schedulerProvider: SchedulerProvider,
                                        private val resourceRepository: ResourceRepository,
                                        private val movieConfigRepository: MovieConfigRepository,
                                        private val logger: Logger) : BaseViewModel() {

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
        _disposable = movieRepository.getMovies(page = page, sortBy = _sortyBy)
                .async(schedulerProvider)
                .loading {
                    if (it) {
                        if (page == 1) {
                            _loading.value = it
                        } else {
                            _moreMovies.value = it
                        }
                    } else {
                        _loading.value = it
                        _moreMovies.value = it
                    }
                }
                .subscribe({
                    val movies = _movies.value
                    if (movies == null) {
                        _movies.value = it.movies
                    } else {
                        movies.addAll(it.movies)
                        _movies.value = movies
                    }
                    countPage = it.countPage
                    page = it.page
                }, {
                    logger.error(it)
                    _toast.value = resourceRepository.getNetworkError()
                })
    }

    fun forceLoad() {
        movieRepository.clearCache()
        _movies.value = null
        page = 1
        load()
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
            if (_moreMovies.value == false && (it > page || it == MovieRepository.UNKNOWN_COUNT_PAGE)) {
                _moreMovies.value = true
                page++
                load()
            }
        }
    }
}