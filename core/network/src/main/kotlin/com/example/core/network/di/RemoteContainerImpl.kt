package com.example.core.network.di

import com.example.core.network.di.annotation.Key
import com.example.core.network.di.annotation.RemoteScope
import com.example.core.network.di.annotation.Url
import com.example.core.network.di.module.InterceptorsModule
import com.example.core.network.di.module.RemoteModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [InterceptorsModule::class, RemoteModule::class])
@RemoteScope
internal interface RemoteContainerImpl : RemoteContainer {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Url url: String,
            @BindsInstance @Key key: String,
        ): RemoteContainerImpl
    }
}