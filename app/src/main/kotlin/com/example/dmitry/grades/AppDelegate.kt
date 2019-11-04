package com.example.dmitry.grades

import android.app.Application
import com.example.core.di.CoreScope
import com.example.di.BaseUIScope
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.di.modules.AppModule
import com.example.dmitry.grades.di.modules.RemoteModule
import toothpick.Toothpick
import toothpick.configuration.Configuration

open class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initBaseScopes()
    }

    private fun initBaseScopes() {
        CoreScope.initCoreScope(this)
        Toothpick.openScopes(CoreScope.NAME, Scopes.APP_SCOPE).apply {
            installModules(AppModule())
        }
        BaseUIScope.initBaseUIScope(Scopes.APP_SCOPE)

        Toothpick.openScopes(BaseUIScope.NAME, Scopes.REMOTE_SCOPE).apply {
            installModules(RemoteModule())
        }
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }
}