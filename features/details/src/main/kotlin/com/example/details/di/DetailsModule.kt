package com.example.details.di

import com.example.details.domain.PrimitiveWrapper
import com.example.details.domain.mappers.MovieMapper
import com.example.details.domain.movie.MovieRepository
import com.example.details.domain.movie.MovieRepositoryImpl
import toothpick.config.Module

internal class DetailsModule(
    private val movieId: Long,
) : Module() {
    init {
        bind(MovieMapper::class.java).to(MovieMapper::class.java).singleton()
        bind(MovieRepository::class.java).to(MovieRepositoryImpl::class.java).singleton()
        bind(PrimitiveWrapper::class.java)
            .withName(MovieId::class.java)
            .toInstance(PrimitiveWrapper(movieId))
    }
}