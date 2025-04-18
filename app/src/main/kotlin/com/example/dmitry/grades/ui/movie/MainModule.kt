package com.example.dmitry.grades.ui.movie

import com.example.navigation.MovieDetailsRouter
import toothpick.config.Module

class MainModule : Module() {
    init {
        bind(MovieDetailsRouter::class.java).to(MovieDetailsRouterImpl::class.java).singleton()
    }
}