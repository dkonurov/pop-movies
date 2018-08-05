package com.example.dmitry.grades.ui.base

import android.os.Build
import android.os.Bundle


open class ToothpickFragment : BaseFragment() {

    private var isSaveState = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createScope(savedInstanceState)
    }

    protected open fun createScope(savedInstanceState: Bundle?) {
        // if need override
    }

    protected open fun closeScope() {
        // if need override
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

        if (Build.VERSION.SDK_INT >= 17) {
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