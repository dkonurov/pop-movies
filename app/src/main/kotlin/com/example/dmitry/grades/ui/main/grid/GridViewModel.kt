package com.example.dmitry.grades.ui.main.grid

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dmitry.grades.domain.models.Movie
import com.example.dmitry.grades.domain.repositories.HttpRepository
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.ui.base.BaseViewModel
import javax.inject.Inject

class GridViewModel @Inject constructor(private val httpRepository: HttpRepository,
                                        private val schedulerProvider: SchedulerProvider,
                                        private val resourceRepository: ResourceRepository) : BaseViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()

    private var page = 1

    private var countPage: Int? = null

    val movies: LiveData<List<Movie>>
        get() = _movies

    fun load() {
        httpRepository.getMovies(page = page)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe {
                    _progress.value = true
                }
                .doOnSuccess {
                    _progress.value = false
                }
                .doOnError {
                    _progress.value = false
                }
                .subscribe({
                    _movies.value = it.movies
                    countPage = it.countPage
                }, {
                    _toast.value = resourceRepository.getNetworkError()
                })
    }

    fun forceLoad() {
        httpRepository.clearCache()
        page = 1
        load()
    }

    fun loadMore() {
//        if (_progress.value == false && ((countPage != null) && (countPage > page) || (countPage == HttpRepository.UNKNOWN_COUNT_PAGE))) {
//
//        }
    }
}