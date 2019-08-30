package com.example.base.ui.observers

import androidx.lifecycle.Observer
import com.example.base.ui.ui.errors.LoadingView

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