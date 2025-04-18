package com.example.base.ui.ui.fragment

import android.content.Context
import com.example.base.extensions.viewModel

abstract class DIFragment<T : Any> : BaseFragment() {
    private lateinit var component: T

    override fun onAttach(context: Context) {
        val viewModel = viewModel { DIHolder(createComponent()) }
        component = viewModel.component
        super.onAttach(context)
    }

    abstract fun createComponent(): T

    fun getComponent(): T = component
}