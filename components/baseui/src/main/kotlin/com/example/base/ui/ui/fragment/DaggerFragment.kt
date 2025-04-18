package com.example.base.ui.ui.fragment

import com.example.di.StoreScope
import toothpick.Scope

abstract class DaggerFragment<T : Any> : DIFragment<T>() {
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

    protected fun getParentToothpickScope(): Scope {
        val store: StoreScope =
            getParentFragmentStore()
                ?: getActivityStore()
                ?: getApplicationStore()
                ?: throw IllegalStateException(
                    "parent fragment or activity or application must be implemented StoreScope",
                )
        return store.getScope()
    }
}