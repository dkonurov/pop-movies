package com.example.dmitry.grades.di.modules

import com.example.dmitry.grades.BuildConfig
import com.example.dmitry.grades.di.ApiKey
import com.example.dmitry.grades.di.AuthInterceptor
import com.example.dmitry.grades.di.LoggingInterceptor
import com.example.dmitry.grades.di.providers.AuthInterceptorProvider
import com.example.dmitry.grades.di.providers.HttpDataSourceProvider
import com.example.dmitry.grades.di.providers.LogInterceptorProvider
import com.example.dmitry.grades.di.providers.OkHttpProvider
import com.example.dmitry.grades.di.providers.RetrofitProvider
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.repositories.HttpRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module
import javax.inject.Inject

class RemoteModule @Inject constructor() : Module() {

    init {
        bind(Interceptor::class.java).withName(LoggingInterceptor::class.java)
                .toProvider(LogInterceptorProvider::class.java).providesSingletonInScope()
        bind(String::class.java).withName(ApiKey::class.java).toInstance(BuildConfig.APY_KEY)
        bind(Interceptor::class.java).withName(AuthInterceptor::class.java)
                .toProvider(AuthInterceptorProvider::class.java).providesSingletonInScope()
        bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java)
                .providesSingletonInScope()
        bind(GsonConverterFactory::class.java).toInstance(GsonConverterFactory.create())
        bind(RxJava2CallAdapterFactory::class.java).toInstance(RxJava2CallAdapterFactory.create())
        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java).providesSingletonInScope()
        bind(HttpDataSource::class.java).toProvider(HttpDataSourceProvider::class.java).providesSingletonInScope()
        bind(HttpRepository::class.java).to(HttpRepository::class.java)
    }
}