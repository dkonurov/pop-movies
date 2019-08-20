package com.example.dmitry.grades.ui.base.observers

import androidx.lifecycle.Observer
import com.example.dmitry.grades.ui.base.ui.errors.ErrorView
import com.example.dmitry.grades.ui.base.ui.errors.UIError

class ErrorObserver(private val view: ErrorView) : Observer<UIError> {

    override fun onChanged(error: UIError?) {
        error?.let {
            view.showMessage(it.message)
        }
    }
}