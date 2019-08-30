package com.example.di

import toothpick.Scope
import toothpick.Toothpick

object BaseUIScope {

    private const val NAME = "BASE_UI_SCOPE"

    private var isInit = false

    fun initBaseUIScope(parent: Scope): Scope {

        return parent.openSubScope(NAME).apply {
            installModules(BaseUIModule())
            isInit = true
        }
    }

    fun getBaseUIScope(): Scope {
        check(isInit) { "Before USE UI Scope need initialization" }
        return Toothpick.openScope(NAME)
    }
}