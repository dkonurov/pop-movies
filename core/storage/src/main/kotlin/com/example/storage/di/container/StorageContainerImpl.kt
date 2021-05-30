package com.example.storage.di.container

import android.content.Context
import com.example.storage.di.module.StorageModule
import com.example.storage.di.scope.StorageScope
import dagger.BindsInstance
import dagger.Component

@StorageScope
@Component(modules = [StorageModule::class])
internal interface StorageContainerImpl : StorageContainer {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): StorageContainerImpl
    }
}