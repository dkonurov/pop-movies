package com.example.core.network.di.module

import com.example.core.network.di.annotation.RemoteScope
import com.example.core.network.di.annotation.Url
import com.example.core.network.model.ServerInfo
import com.example.core.network.remote.HttpDataSource
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
internal class RemoteModule {

    @Provides
    @RemoteScope
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideServerInfo(): ServerInfo = ServerInfo(30, 30, 30)

    @Provides
    @RemoteScope
    fun provideOkhttpClient(
        serverInfo: ServerInfo,
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .also {
                interceptors.forEach { interceptor ->
                    it.addInterceptor(interceptor)
                }
            }
            .connectTimeout(serverInfo.connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(serverInfo.timeoutWrite, TimeUnit.SECONDS)
            .readTimeout(serverInfo.timeoutRead, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @RemoteScope
    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson,
        @Url url: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @RemoteScope
    fun provideHttpDataSource(retrofit: Retrofit): HttpDataSource = retrofit.create(HttpDataSource::class.java)
}