package com.example.dmitry.grades.di.modules

import com.example.core.data.remote.HttpDataSource
import com.example.core.models.config.ServerInfo
import com.example.dmitry.grades.BuildConfig
import com.example.dmitry.grades.di.ApiKey
import com.example.dmitry.grades.di.providers.CoroutineScopeProvider
import com.example.dmitry.grades.di.providers.HttpDataSourceProvider
import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepository
import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepositoryImpl
import com.example.dmitry.grades.domain.repositories.favorite.FavoriteRepository
import com.example.dmitry.grades.domain.repositories.favorite.FavoriteRepositoryImpl
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.domain.schedulers.SchedulerProviderImpl
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import toothpick.config.Module
import javax.inject.Inject

class RemoteModule @Inject constructor() : Module() {

    init {


        //repostories
        bind(MovieRepository::class.java).to(MovieRepositoryImpl::class.java)
        bind(BaseConfigRepository::class.java).to(BaseConfigRepositoryImpl::class.java)
        bind(FavoriteRepository::class.java).to(FavoriteRepositoryImpl::class.java)

        //schedulers
        bind(SchedulerProvider::class.java).toInstance(SchedulerProviderImpl())

        //scopes
        bind(CompletableJob::class.java).toProviderInstance { SupervisorJob() }
        bind(CoroutineScope::class.java).toProvider(CoroutineScopeProvider::class.java)
    }
}