package com.example.core.di

import android.content.Context
import toothpick.Toothpick

object CoreScope {

    const val NAME = "CORE_SCOPE"

    @JvmStatic
    fun initCoreScope(context: Context) {
        Toothpick.openScope(NAME).apply {
            installModules(CoreModule(context))
        }
    }
}