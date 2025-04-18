package com.example.di

import toothpick.Scope
import toothpick.Toothpick

object BaseUIScope {
    private const val NAME = "BASE_UI_SCOPE"

    private var isInit = false

    fun initBaseUIScope(parent: Scope): Scope =
        parent.openSubScope(NAME).apply {
            installModules(RemoteModule())
            isInit = true
        }

    fun getBaseUIScope(): Scope {
        check(isInit) { "Before USE UI Scope need initialization" }
        return Toothpick.openScope(NAME)
    }
}