package com.example.dmitry.grades.di.providers

import com.example.dmitry.grades.di.BaseUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class RetrofitProvider @Inject constructor(private val client: OkHttpClient,
                                           @BaseUrl private val url: String,
                                           private val factory: GsonConverterFactory,
                                           private val adapter: RxJava2CallAdapterFactory) : Provider<Retrofit> {

    override fun get(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(adapter).build()
    }
}