package com.example.dmitry.grades.domain.data.remote

import com.example.dmitry.grades.di.ApiKey
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(@ApiKey private val key: String) : Interceptor {

    companion object {
        const val API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()
        if (originalRequest.method() == "GET") {
            originalRequest = originalRequest.newBuilder().url(
                    originalRequest.url()
                            .newBuilder()
                            .addQueryParameter(API_KEY, key).build()).build()
        }
        return chain.proceed(originalRequest)
    }

}