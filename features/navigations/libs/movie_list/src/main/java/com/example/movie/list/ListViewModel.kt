package com.example.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.schedulers.DispatcherProvider
import com.example.base.ui.ui.errors.UiErrorMapper
import com.example.bottom.navigation.ui.models.FilterType
import com.example.core.coroutine.resultOf
import com.example.core.data.logger.Logger
import com.example.movie.domain.usecase.MovieListUseCase
import com.example.movie.list.view.MovieListRouter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ListViewModel
    @Inject
    constructor(
        private val dispatcherProvider: DispatcherProvider,
        private val logger: Logger,
        private val movieListRouter: MovieListRouter,
        private val movieListUseCase: MovieListUseCase,
        private val uiErrorMapper: UiErrorMapper,
    ) : ViewModel() {
        private val state = MutableStateFlow(ViewModelState())
        private val events = MutableSharedFlow<Event>()

        init {
            events
                .onEach {
                    handleEvents(it)
                }.launchIn(viewModelScope)
        }

        fun observe(): Flow<ListMoviesUiState> =
            state.map {
                if (it.movies.isNotEmpty()) {
                    val lastElement =
                        if (it.isRefreshing) {
                            LastElement.LoadingMore
                        } else if (it.error != null) {
                            LastElement.Refresh
                        } else {
                            null
                        }
                    ListMoviesUiState.Success(it.movies, it.countPage, lastElement)
                } else if (it.isRefreshing) {
                    ListMoviesUiState.Loading
                } else if (it.error != null) {
                    ListMoviesUiState.Error(it.error)
                } else {
                    ListMoviesUiState.Empty
                }
            }

        private suspend fun handleEvents(event: Event) {
            when (event) {
                is Event.NextPage -> {
                    val request = state.value.request
                    load(request.copy(page = request.page + 1))
                }

                is Event.Reload -> forceLoad()
                is Event.Filter -> handleFilter(event.filterType)
            }
        }

        private suspend fun load(request: Request) {
            state.update { it.loading() }
            resultOf {
                withContext(dispatcherProvider.io()) {
                    movieListUseCase.getMovies(MovieListUseCase.Args(page = request.page, sortBy = request.sortBy))
                }
            }.onSuccess { info ->
                state.update { it.success(request, info) }
            }.onFailure { error ->
                logger.error(error)
                state.update { it.error(request, uiErrorMapper.mapEror(error)) }
            }
        }

        fun loadData() {
            viewModelScope.launch {
                events.emit(Event.Reload)
            }
        }

        private suspend fun forceLoad() {
            val vmState = ViewModelState()
            state.value = vmState
            load(vmState.request)
        }

        private suspend fun handleFilter(filterType: FilterType) {
            val vmState = ViewModelState.filter(filterType)
            state.value = vmState
            load(vmState.request)
        }

        fun filter(filterType: FilterType) {
            viewModelScope.launch {
                events.emit(Event.Filter(filterType))
            }
        }

        fun loadMore() {
            viewModelScope.launch {
                events.emit(Event.NextPage)
            }
        }

        fun showDetails(movieId: Long) {
            movieListRouter.showDetails(movieId)
        }
    }