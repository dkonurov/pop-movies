package com.example.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.schedulers.DispatcherProvider
import com.example.bottom.navigation.domain.config.SortConfigRepository
import com.example.bottom.navigation.ui.models.FilterType
import com.example.core.data.logger.Logger
import com.example.core.storage.config.Config
import com.example.core.storage.db.entity.LocalMovie
import com.example.core.storage.preferences.ErrorMessageDataSource
import com.example.movie.domain.usecase.MovieListUseCase
import com.example.movie.list.view.MovieListRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ListViewModel @Inject constructor(
    coroutineScope: CoroutineScope,
    dispatcherProvider: DispatcherProvider,
    errorMessageDataSource: ErrorMessageDataSource,
    logger: Logger,
    private val movieListRouter: MovieListRouter,
    private val movieListUseCase: MovieListUseCase,
    private val sortConfigRepository: SortConfigRepository,
    private val config: Config
) : ViewModel() {

//    private var page = 1
//
//    private var countPage: Int? = null
//
//    private var _sortyBy: String? = sortConfigRepository.sortBy
//
//    private var moreMovies = false

    private val state = MutableStateFlow<State>(State.Empty)

    fun observe(): Flow<State> = state
//
//    fun load() {
//        viewModelScope.launch {
//            val info = movieListUseCase.getMovies(page, _sortyBy)
//            val movies = info.movies
//            val old = _movies.value
//            if (old == null) {
//                _movies.value = movies.toMutableList()
//            } else {
//                old.addAll(movies)
//                _movies.value = old
//            }
//            countPage = info.countPage
//            page = info.page
//        }
//    }
//
//    fun forceLoad() {
//        viewModelScope.launch {
//            movieListUseCase.clearCache()
//            _movies.value = null
//            page = 1
//            load()
//        }
//    }
//
    fun filter(filterType: FilterType) {
//        val text = when (filterType) {
//            FilterType.RELEASE_DATE -> FilterType.RELEASE_DATE.text
//            FilterType.VOTE_COUNT -> FilterType.VOTE_COUNT.text
//            else -> FilterType.POPULARITY.text
//        }
//        if (text != _sortyBy) {
//            sortConfigRepository.sortBy = text
//            _sortyBy = text
//            forceLoad()
//        }
    }
//
//    fun loadMore() {
//        countPage?.let {
//            if (!moreMovies && (it > page || it == config.unknownCountPage)) {
//                moreMovies = true
//                page++
//                load()
//            }
//        }
//    }
//
//    fun showDetails(movieId: Long) {
//        movieListRouter.showDetails(movieId)
//    }
//
    sealed interface State {
        data object Empty : State
        data object Loading : State
        data class Error(val exception: Throwable) : State
        data class Success(val movies: List<LocalMovie>) : State
    }
}

