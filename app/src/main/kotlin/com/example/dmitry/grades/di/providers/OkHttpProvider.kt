package com.example.dmitry.grades.di.providers

import com.example.dmitry.grades.di.AuthInterceptor
import com.example.dmitry.grades.di.LoggingInterceptor
import com.example.dmitry.grades.domain.models.config.ServerInfo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class OkHttpProvider @Inject constructor(@LoggingInterceptor private val interceptor: Interceptor,
                                         @AuthInterceptor private val authInterceptor: Interceptor,
                                         private val serverInfo: ServerInfo) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(serverInfo.connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(serverInfo.timeoutWrite, TimeUnit.SECONDS)
                .readTimeout(serverInfo.timeoutRead, TimeUnit.SECONDS)
                .build()
    }
}