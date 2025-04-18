package com.example.base.ui.ui.errors

import com.example.base.ui.ui.errors.UIError
import com.example.core.storage.preferences.ErrorMessageDataSource
import javax.inject.Inject

class UiErrorMapper
    @Inject
    constructor(
        private val errorMessageDataSource: ErrorMessageDataSource,
    ) {
        fun mapEror(throwable: Throwable): UIError {
            return UIError(throwable, "test")
//        return when {
//            t is HttpException -> handleHttpException(t)
//            NETWORK_EXCEPTIONS.contains(t.javaClass) -> handleNetworkException(t)
//            else -> handleUnexpectedError(t)
//        }
        }

//    private fun handleNetworkException(t: Throwable): UIError {
//        return UIError(t, errorMessageDataSource.getNetworkError())
//    }
//
//    private fun handleHttpException(e: HttpException): UIError {
//        if (e.statusCode == AUTH_ERROR_HTTP_CODE) {
//            return UIError(e, errorMessageDataSource.getNotAuthError())
//        } else {
//            return handleUnexpectedError(e)
//        }
//    }
//
//    private fun handleUnexpectedError(e: Throwable): UIError {
//        return UIError(e, errorMessageDataSource.getUnknownError())
//    }
//
//    companion object {
//        const val AUTH_ERROR_HTTP_CODE = 401
//        val NETWORK_EXCEPTIONS = listOf<Class<*>>(
//            UnknownHostException::class.java,
//            SocketTimeoutException::class.java,
//            ConnectException::class.java
//        )
//    }
    }