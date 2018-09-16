package com.example.dmitry.grades.ui.base.ui.errors

import android.arch.lifecycle.LifecycleOwner

interface ErrorView : LifecycleOwner {
    fun showMessage(message: String)
}