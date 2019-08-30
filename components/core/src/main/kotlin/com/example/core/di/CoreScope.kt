package com.example.core.di

import android.content.Context
import toothpick.Scope
import toothpick.Toothpick

object CoreScope {

    private const val NAME = "CORE_SCOPE"

    private var isInit = false

    fun initCoreScope(context: Context): Scope {
        return Toothpick.openScope(NAME).apply {
            installModules(CoreModule(context))
            isInit = true
        }

    }

    fun getCoreScope(): Scope {
        check(isInit) { "Before Use Core Scope need initialization it" }
        return Toothpick.openScope(NAME)
    }
}