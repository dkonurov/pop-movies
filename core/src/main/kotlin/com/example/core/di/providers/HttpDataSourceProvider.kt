package com.example.core.di.providers

import com.example.core.BuildConfig
import com.example.core.data.remote.AuthInterceptor
import com.example.core.data.remote.HttpDataSource
import com.example.core.models.config.ServerInfo
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

internal class HttpDataSourceProvider @Inject constructor(
    private val gson: Gson
) : Provider<HttpDataSource> {
    override fun get(): HttpDataSource {
        val serverInfo = ServerInfo(30, 30, 30)
        val retrofit = provideRetrofit(BuildConfig.PROD_URL, BuildConfig.APY_KEY, serverInfo)
        return retrofit.create(HttpDataSource::class.java)
    }

    private fun provideOkhttpClient(key: String, serverInfo: ServerInfo): OkHttpClient {
        val httpLogger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(key))
                .addInterceptor(httpLogger)
                .connectTimeout(serverInfo.connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(serverInfo.timeoutWrite, TimeUnit.SECONDS)
                .readTimeout(serverInfo.timeoutRead, TimeUnit.SECONDS)
                .build()
    }

    private fun provideRetrofit(url: String, key: String, serverInfo: ServerInfo) =
            Retrofit.Builder()
                    .baseUrl(url)
                    .client(provideOkhttpClient(key, serverInfo))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
}