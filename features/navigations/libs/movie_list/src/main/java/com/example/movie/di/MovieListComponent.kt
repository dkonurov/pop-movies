package com.example.movie.di

import com.example.movie.list.ListViewModel
import dagger.Component

@Component(dependencies = [MovieListDependencies::class])
internal interface MovieListComponent {
    fun getListViewModel(): ListViewModel

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: MovieListDependencies,
        ): MovieListComponent
    }
}