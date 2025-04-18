package com.example.base.ui.observers

import androidx.lifecycle.Observer
import com.example.base.ui.ui.errors.ErrorView
import com.example.base.ui.ui.errors.UIError

class ErrorObserver(
    private val view: ErrorView,
) : Observer<UIError?> {
    override fun onChanged(value: UIError?) {
        val message = value?.message
        if (message != null) {
            view.showMessage(value.message)
        }
    }
}