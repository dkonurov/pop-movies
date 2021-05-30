package com.example.core.network.remote

import com.example.core.network.di.annotation.Key
import com.example.core.network.di.annotation.RemoteScope
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@RemoteScope
internal class AuthInterceptor @Inject constructor(
    @Key private val key: String
) : Interceptor {

    companion object {
        const val API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()
        if (originalRequest.method == "GET") {
            originalRequest = originalRequest.newBuilder().url(
                originalRequest.url
                    .newBuilder()
                    .addQueryParameter(API_KEY, key).build()
            ).build()
        }
        return chain.proceed(originalRequest)
    }
}