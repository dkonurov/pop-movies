package com.example.dmitry.grades.ui.base.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dmitry.grades.domain.Logger
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.ui.base.ui.errors.UIError
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

open class ErrorViewModel @Inject constructor(private val resourceRepository: ResourceRepository,
                                              private val logger: Logger) : BaseViewModel() {


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
            ErrorViewModel.NETWORK_EXCEPTIONS.contains(t.javaClass) -> handleNetworkException(t)
            else -> handleUnexpectedError(t)
        }
    }

    private fun handleNetworkException(t: Throwable) {
        if (_noStub.hasObservers()) {
            _noStub.value = true
        } else {
            _error.value = UIError(t, resourceRepository.getNetworkError())
        }
    }

    override fun hideError() {
        _noStub.value = false
        _error.value = null
    }

    protected fun handleHttpException(e: HttpException) {
        if (e.code() == AUTH_ERROR_HTTP_CODE) {
            _error.value = UIError(e, resourceRepository.getNotAuthError())
        } else {
            handleUnexpectedError(e)
        }
    }

    protected fun handleUnexpectedError(e: Throwable) {
        _error.value = UIError(e, resourceRepository.getUnknownError())
    }

    override fun showLoading() {
        _loading.value = true
    }

    override fun hideLoading() {
        _loading.value = false
    }

}