package com.example.base.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.schedulers.DispatcherProvider
import com.example.base.ui.ui.errors.UIError
import com.example.base.ui.ui.errors.UiErrorMapper
import com.example.core.data.logger.Logger
import kotlinx.coroutines.CoroutineScope

open class ErrorViewModel(
    coroutineScope: CoroutineScope,
    schedulerProvider: DispatcherProvider,
    private val logger: Logger,
    private val uiErrorMapper: UiErrorMapper,
) : BaseViewModel(coroutineScope, schedulerProvider) {
    val error: LiveData<UIError?>
        get() = _error

    protected val _error = SingleLiveEvent<UIError?>()

    val loading: LiveData<Boolean>
        get() = _loading

    protected val _loading = MutableLiveData<Boolean>()

    override fun handleError(t: Throwable) {
        logger.error(t)
        val uiError = this.uiErrorMapper.mapEror(t)
        _error.value = uiError
    }

    override fun hideError() {
        _error.value = null
    }

    override fun showLoading() {
        _loading.value = true
    }

    override fun hideLoading() {
        _loading.value = false
    }
}