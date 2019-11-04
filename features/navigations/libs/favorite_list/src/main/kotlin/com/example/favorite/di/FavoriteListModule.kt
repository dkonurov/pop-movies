package com.example.favorite.di

import com.example.favorite.domain.repositories.FavoriteListRepository
import com.example.favorite.domain.repositories.FavoriteListRepositoryImpl
import com.example.favorite.list.FavoriteViewModel
import toothpick.config.Module

internal class FavoriteListModule : Module() {

    init {
        bind(FavoriteViewModel::class.java).to(FavoriteViewModel::class.java).singleton()
        bind(FavoriteListRepository::class.java).to(FavoriteListRepositoryImpl::class.java)
                .singleton()
    }
}