package com.example.base.ui.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.di.StoreScope
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module

open class DIActivity : AppCompatActivity(), StoreScope {

    private lateinit var scope: Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = getApplicationScope().openSubScope(this.javaClass.simpleName)
        if (savedInstanceState == null) {
            val modules = getModules()
            if (modules != null) {
                scope.installModules(*modules)
            }
        }
        scope.inject(this)
    }

    override fun getScope(): Scope = scope

    private fun getApplicationScope(): Scope {
        val store = application as? StoreScope
                ?: throw IllegalStateException("application must be implemented StoreScope")
        return store.getScope()
    }

    protected open fun getModules(): Array<Module>? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            closeScope()
        }
    }

    protected open fun closeScope() {
        Toothpick.closeScope(scope.name)
    }
}