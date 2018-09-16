package com.example.dmitry.grades.ui.base.ui.errors

import com.example.dmitry.grades.ui.base.observers.ErrorObserver
import com.example.dmitry.grades.ui.base.observers.NoStubObserver
import com.example.dmitry.grades.ui.base.vm.ErrorViewModel

class ErrorHandler {


    companion object {
        fun handleError(viewModel: ErrorViewModel, view: ErrorView) {
            viewModel.error.observe(view, ErrorObserver(view))
        }

        fun handleError(viewModel: ErrorViewModel, view: StubErrorView) {
            viewModel.error.observe(view, ErrorObserver(view))
            viewModel.notStub.observe(view, NoStubObserver(view))
        }
    }
}