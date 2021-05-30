package com.example.dmitry.grades

import android.app.Application
import com.example.core.di.CoreDependenicesFactories
import com.example.core.network.di.RemoteContainerFactory
import com.example.di.BaseUIScope
import com.example.di.StoreScope
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.di.modules.AppModule
import com.example.storage.di.container.StorageContainerFactory
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration

open class AppDelegate : Application(), StoreScope {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initScopes()
    }

    private fun initScopes() {
        val coreContainer = CoreDependenicesFactories.create()
        val remoteContainer = RemoteContainerFactory.create(BuildConfig.PROD_URL, BuildConfig.APY_KEY)
        val storageContainer = StorageContainerFactory.create(this)
        val appScore = Toothpick.openScope(Scopes.APP_SCOPE)
            .installModules(
                AppModule(
                    this,
                    remoteContainer,
                    storageContainer,
                    coreContainer
                )
            )

        BaseUIScope.initBaseUIScope(appScore)
    }

    override fun getScope(): Scope = BaseUIScope.getBaseUIScope()

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }
}