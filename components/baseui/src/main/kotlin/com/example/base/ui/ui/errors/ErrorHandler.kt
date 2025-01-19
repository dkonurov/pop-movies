package com.example.base.ui.ui.errors

import com.example.base.ui.observers.ErrorObserver
import com.example.base.ui.vm.ErrorViewModel

object ErrorHandler {

    @JvmStatic
    fun handleError(viewModel: ErrorViewModel, view: ErrorView) {
        viewModel.error.observe(view, ErrorObserver(view))
    }
}