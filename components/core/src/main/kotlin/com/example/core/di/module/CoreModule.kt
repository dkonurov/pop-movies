package com.example.core.di.module

import com.example.core.data.logger.Logger
import com.example.core.data.logger.LoggerImpl
import com.example.core.di.scope.CoreScope
import dagger.Binds
import dagger.Module

@Module
internal abstract class CoreModule {

    @Binds
    @CoreScope
    abstract fun bindLogger(impl: LoggerImpl): Logger
}