package com.example.dmitry.grades.ui.main.grid

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dmitry.grades.domain.models.Movie
import com.example.dmitry.grades.domain.repositories.HttpRepository
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.ui.base.BaseViewModel
import com.example.dmitry.grades.ui.base.async
import com.example.dmitry.grades.ui.base.loading
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class GridViewModel @Inject constructor(private val httpRepository: HttpRepository,
                                        private val schedulerProvider: SchedulerProvider,
                                        private val resourceRepository: ResourceRepository) : BaseViewModel() {

    private val _movies = MutableLiveData<MutableList<Movie>>()

    private var page = 1

    private var countPage: Int? = null

    private var _moreMovies = MutableLiveData<Boolean>()

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
        _disposable = httpRepository.getMovies(page = page)
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
                    _toast.value = resourceRepository.getNetworkError()
                })
    }

    fun forceLoad() {
        httpRepository.clearCache()
        _movies.value = null
        page = 1
        load()
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
            if (_moreMovies.value == false && (it > page || it == HttpRepository.UNKNOWN_COUNT_PAGE)) {
                _moreMovies.value = true
                page++
                load()
            }
        }
    }
}