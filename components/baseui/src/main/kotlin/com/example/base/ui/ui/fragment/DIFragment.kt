package com.example.base.ui.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.example.di.StoreScope
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module

open class DIFragment : BaseFragment(), StoreScope {

    private var isSaveState = false

    private lateinit var scope: Scope

    override fun onAttach(context: Context?) {
        scope = getParentScope().openSubScope(this.javaClass.simpleName)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val modules = getModules()
            if (modules != null) {
                scope.installModules(*modules)
            }
        }
        scope.inject(this)
    }

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
        val store: StoreScope = getParentFragmentStore()
            ?: getActivityStore()
            ?: getApplicationStore()
            ?: throw IllegalStateException(
                "parent fragment or activity or application must be implemented StoreScope"
            )
        return store.getScope()
    }

    protected open fun getModules(): Array<Module>? {
        return null
    }

    override fun getScope(): Scope = scope

    private fun closeScope() {
        Toothpick.closeScope(scope.name)
    }

    override fun onStart() {
        super.onStart()

        isSaveState = false
    }

    override fun onResume() {
        super.onResume()

        isSaveState = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        isSaveState = true
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onDestroy() {
        super.onDestroy()

        activity?.let {
            if (it.isFinishing) {
                closeScope()
                return
            }
        }

        if (isSaveState) {
            isSaveState = false
            return
        }

        var anyParentIsRemoving = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            var parent = parentFragment
            while (!anyParentIsRemoving && parent != null) {
                anyParentIsRemoving = parent.isRemoving
                parent = parent.parentFragment
            }
        }

        if (isRemoving || anyParentIsRemoving) {
            closeScope()
        }
    }
}