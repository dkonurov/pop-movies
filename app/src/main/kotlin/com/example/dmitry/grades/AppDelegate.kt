package com.example.dmitry.grades

import android.app.Application
import com.example.core.di.CoreScope
import com.example.di.BaseUIScope
import com.example.di.StoreScope
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.di.modules.AppModule
import com.example.dmitry.grades.di.modules.RemoteModule
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
        val appScore = CoreScope.initCoreScope(this)
                .openSubScope(Scopes.APP_SCOPE)
                .installModules(AppModule())

        BaseUIScope.initBaseUIScope(appScore)
                .openSubScope(Scopes.REMOTE_SCOPE)
                .installModules(RemoteModule())
    }

    override fun getScope(): Scope = Toothpick.openScope(Scopes.REMOTE_SCOPE)

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }
}