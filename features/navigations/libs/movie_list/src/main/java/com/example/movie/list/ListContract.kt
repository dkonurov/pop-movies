package com.example.movie.list

import com.example.base.ui.ui.errors.UIError
import com.example.bottom.navigation.ui.models.FilterType
import com.example.bottom.navigation.ui.models.MovieListInfo
import com.example.core.storage.db.entity.LocalMovie

data class ViewModelState(
    val request: Request = Request(),
    val movies: List<LocalMovie> = emptyList(),
    val countPage: Int = 0,
    val isRefreshing: Boolean = false,
    val error: UIError? = null
) {

    fun loading() = copy(
        isRefreshing = true,
        error = null
    )

    fun success(request: Request, info: MovieListInfo) = copy(
        request = request,
        movies = this.movies + info.movies,
        countPage = info.countPage,
        isRefreshing = false
    )

    fun error(request: Request, error: UIError) = copy(
        request = request,
        error = error,
        isRefreshing = false
    )

    companion object {
        fun filter(filterType: FilterType): ViewModelState = ViewModelState(
            request = Request(sortBy = filterType.text)
        )
    }
}


sealed interface LastElement {
    data object LoadingMore : LastElement
    data object Refresh : LastElement
}


sealed interface ListMoviesUiState {

    data object Empty : ListMoviesUiState

    data object Loading : ListMoviesUiState

    data class Error(val ui: UIError) : ListMoviesUiState

    data class Success(
        val movies: List<LocalMovie>,
        val countPage: Int = 0,
        val lastElement: LastElement? = null
    ) : ListMoviesUiState
}

data class Request(
    val page: Int = 1,
    val sortBy: String? = null
)


sealed interface Event {
    data object NextPage : Event
    data object Reload : Event
    data class Filter(val filterType: FilterType) : Event
}