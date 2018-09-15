package com.example.dmitry.grades.ui.base.vm

import android.arch.lifecycle.LiveData
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

    protected val _error = SingleLiveEvent<UIError>()

    override fun handleError(t: Throwable) {
        logger.error(t)
        when {
            t is HttpException -> handleHttpException(t)
            else -> handleUnexpectedError(t)
        }
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

}