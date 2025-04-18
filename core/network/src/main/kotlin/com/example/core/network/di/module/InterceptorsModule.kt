package com.example.core.network.di.module

import com.example.core.network.di.annotation.RemoteScope
import com.example.core.network.remote.AuthInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
internal abstract class InterceptorsModule {
    companion object {
        @Provides
        @RemoteScope
        fun provideHttpLogingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

    @IntoSet
    @Binds
    abstract fun bindLoggingIntereceptors(impl: HttpLoggingInterceptor): Interceptor

    @IntoSet
    @Binds
    abstract fun bindAuthInterceptor(impl: AuthInterceptor): Interceptor
}