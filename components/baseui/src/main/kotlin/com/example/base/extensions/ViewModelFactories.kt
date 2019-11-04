package com.example.base.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> FragmentActivity.viewModel(crossinline init: () -> T): T {
    return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = init.invoke() as T
    }).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.viewModel(crossinline init: () -> T): T {
    return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = init.invoke() as T
    }).get(T::class.java)
}