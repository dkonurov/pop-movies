package com.example.base.ui.ui.fragment

import android.content.Context
import com.example.base.extensions.viewModel
import com.example.di.StoreScope

abstract class DIFragment<T : Any> : BaseFragment(), StoreScope {

    private lateinit var component: T

    override fun onAttach(context: Context) {
        val viewModel = viewModel { DIHolder(createComponent()) }
        component = viewModel.component
        super.onAttach(context)
    }


    abstract fun createComponent(): T

    fun getComponent(): T = component
}