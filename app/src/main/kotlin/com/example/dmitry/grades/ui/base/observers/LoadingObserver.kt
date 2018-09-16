package com.example.dmitry.grades.ui.base.observers

import android.arch.lifecycle.Observer
import com.example.dmitry.grades.ui.base.ui.errors.LoadingView

class LoadingObserver(private val loadingView: LoadingView) : Observer<Boolean> {

    override fun onChanged(state: Boolean?) {
        state?.let {
            if (it) {
                loadingView.showLoading()
            } else {
                loadingView.hideLoading()
            }
        }
    }
}