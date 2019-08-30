package com.example.bottom.navigation.di

import com.example.bottom.navigation.domain.config.SortConfigRepository
import com.example.bottom.navigation.domain.config.SortConfigRepositoryImpl
import toothpick.config.Module

class BottomNavigationModule : Module() {

    init {
        bind(SortConfigRepository::class.java).to(SortConfigRepositoryImpl::class.java)
    }
}