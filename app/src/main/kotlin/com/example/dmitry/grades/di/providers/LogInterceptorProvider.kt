package com.example.dmitry.grades.di.providers

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Provider

class LogInterceptorProvider : Provider<Interceptor> {
    override fun get(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}