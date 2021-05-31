package com.example.core.di.module

import com.example.core.data.logger.Logger
import com.example.core.data.logger.LoggerImpl
import com.example.core.di.scope.CoreScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
internal abstract class CoreModule {

    companion object {
        @Provides
        fun provideCoroutineScope(): CoroutineScope {
            val job = SupervisorJob()
            return CoroutineScope(Dispatchers.Main.immediate + job)
        }
    }

    @Binds
    @CoreScope
    abstract fun bindLogger(impl: LoggerImpl): Logger
}