package com.example.base.ui.ui.errors

import androidx.lifecycle.LifecycleOwner

interface ErrorView : LifecycleOwner {
    fun showMessage(message: String)
}