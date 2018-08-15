package com.example.dmitry.grades.di.modules

import com.example.dmitry.grades.BuildConfig
import com.example.dmitry.grades.di.ApiKey
import com.example.dmitry.grades.di.Auth
import com.example.dmitry.grades.di.LoggingInterceptor
import com.example.dmitry.grades.di.providers.HttpDataSourceProvider
import com.example.dmitry.grades.di.providers.OkHttpProvider
import com.example.dmitry.grades.di.providers.RetrofitProvider
import com.example.dmitry.grades.domain.data.remote.AuthInterceptor
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.models.config.ServerInfo
import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepository
import com.example.dmitry.grades.domain.repositories.configurtaion.BaseConfigRepositoryImpl
import com.example.dmitry.grades.domain.repositories.favorite.FavoriteRepository
import com.example.dmitry.grades.domain.repositories.favorite.FavoriteRepositoryImpl
import com.example.dmitry.grades.domain.repositories.movie.MovieRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.domain.schedulers.SchedulerProviderImpl
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module
import javax.inject.Inject

class RemoteModule @Inject constructor() : Module() {

    init {
        //remote
        bind(Interceptor::class.java).withName(LoggingInterceptor::class.java)
                .toInstance(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        val gson = Gson()
        bind(Gson::class.java).toInstance(gson)
        bind(String::class.java).withName(ApiKey::class.java).toInstance(BuildConfig.APY_KEY)
        bind(Interceptor::class.java).withName(Auth::class.java)
                .to(AuthInterceptor::class.java).singletonInScope()
        bind(ServerInfo::class.java).toInstance(ServerInfo(30, 30, 30))
        bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java)
                .providesSingletonInScope()
        bind(GsonConverterFactory::class.java).toInstance(GsonConverterFactory.create(gson))
        bind(RxJava2CallAdapterFactory::class.java).toInstance(RxJava2CallAdapterFactory.create())
        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java).providesSingletonInScope()
        bind(HttpDataSource::class.java).toProvider(HttpDataSourceProvider::class.java).providesSingletonInScope()

        //repostories
        bind(MovieRepository::class.java).to(MovieRepositoryImpl::class.java)
        bind(BaseConfigRepository::class.java).to(BaseConfigRepositoryImpl::class.java)
        bind(FavoriteRepository::class.java).to(FavoriteRepositoryImpl::class.java)

        //schedulers
        bind(SchedulerProvider::class.java).toInstance(SchedulerProviderImpl())
    }
}