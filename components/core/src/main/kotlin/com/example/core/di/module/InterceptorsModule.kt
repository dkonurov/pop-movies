package com.example.core.di.module

import com.example.core.di.scope.CoreScope
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
        @CoreScope
        fun provideHttpLogingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @IntoSet
    @Binds
    abstract fun bindLoggingIntereceptors(impl: HttpLoggingInterceptor): Interceptor
}