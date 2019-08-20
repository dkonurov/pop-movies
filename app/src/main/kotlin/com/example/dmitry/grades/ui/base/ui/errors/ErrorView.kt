package com.example.dmitry.grades.ui.base.ui.errors

import androidx.lifecycle.LifecycleOwner

interface ErrorView : LifecycleOwner {
    fun showMessage(message: String)
}