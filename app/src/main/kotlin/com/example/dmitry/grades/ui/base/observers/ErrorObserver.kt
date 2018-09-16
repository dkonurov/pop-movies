package com.example.dmitry.grades.ui.base.observers

import android.arch.lifecycle.Observer
import com.example.dmitry.grades.ui.base.ui.errors.ErrorView
import com.example.dmitry.grades.ui.base.ui.errors.UIError

class ErrorObserver : Observer<UIError> {

    private val view: ErrorView

    constructor(errorView: ErrorView) {
        view = errorView
    }

    override fun onChanged(error: UIError?) {
        error?.let {
            view.showMessage(it.message)
        }
    }
}