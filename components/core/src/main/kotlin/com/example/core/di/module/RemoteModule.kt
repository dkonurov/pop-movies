package com.example.core.di.module

import com.example.core.BuildConfig
import com.example.core.data.remote.AuthInterceptor
import com.example.core.data.remote.HttpDataSource
import com.example.core.di.scope.CoreScope
import com.example.core.models.config.ServerInfo
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
    fun provideServerInfo(): ServerInfo = ServerInfo(30, 30, 30)

    @Provides
    @CoreScope
    fun provideOkhttpClient(serverInfo: ServerInfo, interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(BuildConfig.APY_KEY))
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
    @CoreScope
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.PROD_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @CoreScope
    fun provideHttpDataSource(retrofit: Retrofit): HttpDataSource = retrofit.create(HttpDataSource::class.java)
}