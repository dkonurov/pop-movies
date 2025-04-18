package com.example.dmitry.grades.ui.movie

import com.example.base.ui.vm.SingleLiveEvent
import com.example.navigation.MovieDetailsRouter
import javax.inject.Inject

class MovieDetailsRouterImpl
    @Inject
    constructor() : MovieDetailsRouter {
        override fun showDetails(movieId: Long) {
            screens.postValue(movieId)
        }

        override val screens = SingleLiveEvent<Long>()
    }