package com.example.base.ui.ui.fragment

import android.os.Bundle
import com.example.di.StoreScope
import toothpick.Scope
import toothpick.config.Module

open class ToothpickFragment :
    DIFragment<ToothpickComponent>(),
    StoreScope {
    override fun createComponent(): ToothpickComponent =
        ToothpickComponent(
            getParentScope().openSubScope(this.javaClass.simpleName),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scope = getComponent().scope
        if (savedInstanceState == null) {
            val modules = getModules()
            if (modules != null) {
                scope.installModules(*modules)
            }
        }
        scope.inject(this)
    }

    override fun getScope(): Scope = getComponent().scope

    private fun getApplicationStore(): StoreScope? {
        val application = requireActivity().application
        return if (application is StoreScope) {
            application
        } else {
            null
        }
    }

    private fun getParentFragmentStore(): StoreScope? {
        while (parentFragment != null) {
            val parent = parentFragment
            if (parent is StoreScope) {
                return parent
            }
        }
        return null
    }

    private fun getActivityStore(): StoreScope? {
        val activity = requireActivity()
        return if (activity is StoreScope) {
            activity
        } else {
            null
        }
    }

    private fun getParentScope(): Scope {
        val store: StoreScope =
            getParentFragmentStore()
                ?: getActivityStore()
                ?: getApplicationStore()
                ?: throw IllegalStateException(
                    "parent fragment or activity or application must be implemented StoreScope",
                )
        return store.getScope()
    }

    protected open fun getModules(): Array<Module>? = null
}