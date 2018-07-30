package com.example.dmitry.grades

import android.app.Application
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.di.modules.AppModule
import com.example.dmitry.grades.di.modules.RemoteModule
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator

class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initBaseScopes()

    }

    private fun initBaseScopes() {
        val appScope = Toothpick.openScope(Scopes.APP_SCOPE)
        appScope.installModules(AppModule(this))

        val remoteScope = Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.REMOTE_SCOPE)
        remoteScope.installModules(RemoteModule())
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
            FactoryRegistryLocator.setRootRegistry(com.example.dmitry.grades.FactoryRegistry())
            MemberInjectorRegistryLocator.setRootRegistry(com.example.dmitry.grades.MemberInjectorRegistry())
        }
    }

}