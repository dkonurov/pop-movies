package com.example.core.storage.di.container

import android.content.Context
import com.example.core.storage.di.module.StorageModule
import com.example.core.storage.di.scope.StorageScope
import dagger.BindsInstance
import dagger.Component

@StorageScope
@Component(modules = [StorageModule::class])
internal interface StorageContainerImpl : StorageContainer {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): StorageContainerImpl
    }
}