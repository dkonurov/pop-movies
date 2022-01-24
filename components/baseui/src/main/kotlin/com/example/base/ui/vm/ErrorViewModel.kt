package com.example.base.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.HttpException
import com.example.base.schedulers.SchedulerProvider
import com.example.base.ui.ui.errors.UIError
import com.example.core.data.logger.Logger
import com.example.core.storage.preferences.ErrorMessageDataSource
import kotlinx.coroutines.CoroutineScope
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class ErrorViewModel(
    coroutineScope: CoroutineScope,
    schedulerProvider: SchedulerProvider,
    private val errorMessageDataSource: ErrorMessageDataSource,
    private val logger: Logger
) : BaseViewModel(coroutineScope, schedulerProvider) {

    companion object {
        const val AUTH_ERROR_HTTP_CODE = 401
        val NETWORK_EXCEPTIONS = listOf<Class<*>>(
            UnknownHostException::class.java,
            SocketTimeoutException::class.java,
            ConnectException::class.java
        )
    }

    val error: LiveData<UIError>
        get() = _error

    val notStub: LiveData<Boolean>
        get() = _noStub

    protected val _error = SingleLiveEvent<UIError>()

    protected val _noStub = MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading

    protected val _loading = MutableLiveData<Boolean>()

    override fun handleError(t: Throwable) {
        logger.error(t)
        when {
            t is HttpException -> handleHttpException(t)
            NETWORK_EXCEPTIONS.contains(t.javaClass) -> handleNetworkException(t)
            else -> handleUnexpectedError(t)
        }
    }

    private fun handleNetworkException(t: Throwable) {
        if (_noStub.hasObservers()) {
            _noStub.value = true
        } else {
            _error.value = UIError(t, errorMessageDataSource.getNetworkError())
        }
    }

    override fun hideError() {
        _noStub.value = false
        _error.value = null
    }

    protected fun handleHttpException(e: HttpException) {
        if (e.statusCode == AUTH_ERROR_HTTP_CODE) {
            _error.value = UIError(e, errorMessageDataSource.getNotAuthError())
        } else {
            handleUnexpectedError(e)
        }
    }

    protected fun handleUnexpectedError(e: Throwable) {
        _error.value = UIError(e, errorMessageDataSource.getUnknownError())
    }

    override fun showLoading() {
        _loading.value = true
    }

    override fun hideLoading() {
        _loading.value = false
    }
}