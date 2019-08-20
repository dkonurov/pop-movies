package com.example.dmitry.grades.ui.base.observers

import androidx.lifecycle.Observer
import com.example.dmitry.grades.ui.base.ui.errors.StubErrorView

class NoStubObserver(private val view: StubErrorView) : Observer<Boolean> {

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