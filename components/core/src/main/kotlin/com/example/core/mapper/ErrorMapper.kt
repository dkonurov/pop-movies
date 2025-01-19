package com.example.core.mapper

import com.example.core.model.PresentationError
import com.example.core.storage.preferences.ErrorMessageDataSource
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorMapper @Inject constructor(
    private val errorMessageDataSource: ErrorMessageDataSource
) {

    fun map(throwable: Throwable): PresentationError {
        val message = when {
            throwable is HttpException -> handleHttpException(throwable)
            NETWORK_EXCEPTIONS.contains(throwable.javaClass) -> errorMessageDataSource.getNetworkError()
            else -> errorMessageDataSource.getUnknownError()
        }
        return PresentationError(message, throwable)
    }

    private fun handleHttpException(exception: HttpException): String {
        return if (exception.code() == AUTH_ERROR_HTTP_CODE) {
            errorMessageDataSource.getNotAuthError()
        } else {
            errorMessageDataSource.getUnknownError()
        }
    }

    companion object {
        const val AUTH_ERROR_HTTP_CODE = 401
        val NETWORK_EXCEPTIONS = listOf<Class<*>>(
            UnknownHostException::class.java,
            SocketTimeoutException::class.java,
            ConnectException::class.java
        )
    }
}