package com.example.dmitry.grades.ui.base.observers

import android.arch.lifecycle.Observer
import com.example.dmitry.grades.ui.base.ui.errors.ErrorView
import com.example.dmitry.grades.ui.base.ui.errors.StubErrorView
import com.example.dmitry.grades.ui.base.vm.ErrorViewModel
import com.example.dmitry.grades.ui.base.ui.errors.UIError

class ErrorObserver : Observer<UIError> {

    private val view: ErrorView

    constructor(errorView: ErrorView) {
        view = errorView
    }

    constructor(stubErrorView: StubErrorView) {
        view = stubErrorView
    }

    override fun onChanged(error: UIError?) {
        error?.let {
            if (view is StubErrorView) {
                handleError(view, it)
            } else {
                handleError(view, it)
            }
        }
    }

    fun handleError(errorView: ErrorView, error: UIError) {
        errorView.showMessage(error.message)
    }

    fun handleError(stubErrorView: StubErrorView, error: UIError) {
        if (ErrorViewModel.NETWORK_EXCEPTIONS.contains(error.throwable.javaClass)) {
            stubErrorView.showStub()
        } else {
            handleError(stubErrorView as ErrorView, error)
        }
    }
}