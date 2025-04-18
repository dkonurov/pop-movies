package com.example.base.ui.observers

import androidx.lifecycle.Observer
import com.example.base.ui.ui.errors.StubErrorView

class NoStubObserver(
    private val view: StubErrorView,
) : Observer<Boolean> {
    override fun onChanged(value: Boolean) {
        if (value) {
            view.showStub()
        } else {
            view.hideStub()
        }
    }
}