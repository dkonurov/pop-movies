package com.example.dmitry.grades.ui.base.observers

import android.arch.lifecycle.Observer
import com.example.dmitry.grades.ui.base.ui.errors.ErrorView
import com.example.dmitry.grades.ui.base.ui.errors.StubErrorView
import com.example.dmitry.grades.ui.base.ui.errors.UIError

class NoStubObserver : Observer<Boolean> {

    private val view: StubErrorView

    constructor(stubErrorView: StubErrorView) {
        view = stubErrorView
    }

    override fun onChanged(state: Boolean?) {
        state?.let {
            if (it) {
                view.showStub()
            } else {
                view.hideStub()
            }
        }
    }
}