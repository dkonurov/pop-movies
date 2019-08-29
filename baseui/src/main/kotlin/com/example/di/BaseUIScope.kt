package com.example.di

import toothpick.Toothpick

object BaseUIScope {

    const val NAME = "BASE_UI_SCOPE"

    fun initBaseUIScope(parent: String) {
        Toothpick.openScopes(parent, NAME).apply {
            installModules(BaseUIModule())
        }
    }
}