package com.example.dmitry.grades.di.providers

import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Provider

class HttpDataSourceProvider @Inject constructor(val retrofit: Retrofit) : Provider<HttpDataSource> {
    override fun get(): HttpDataSource {
        return retrofit.create(HttpDataSource::class.java)
    }
}