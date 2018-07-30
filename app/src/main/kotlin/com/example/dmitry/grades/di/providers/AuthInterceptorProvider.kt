package com.example.dmitry.grades.di.providers

import com.example.dmitry.grades.di.ApiKey
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptorProvider @Inject constructor(@ApiKey val key: String) : Provider<Interceptor> {
    override fun get(): Interceptor {
        return Interceptor {
            var originalRequest = it.request()
            if (originalRequest.method() == "GET") {
                originalRequest = originalRequest.newBuilder().url(
                        originalRequest.url()
                                .newBuilder()
                                .addQueryParameter("api_key", key).build()).build()
            }
            it.proceed(originalRequest)
        }
    }
}