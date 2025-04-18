package com.example.core.mapper

import com.example.core.data.NetworkUtils
import com.example.core.model.PresentationError
import com.example.core.storage.preferences.ErrorMessageDataSource
import retrofit2.HttpException
import javax.inject.Inject

class ErrorMapper
    @Inject
    constructor(
        private val errorMessageDataSource: ErrorMessageDataSource,
    ) {
        fun map(throwable: Throwable): PresentationError {
            val message =
                when {
                    throwable is HttpException -> handleHttpException(throwable)
                    NetworkUtils.isNetworkException(throwable) -> errorMessageDataSource.getNetworkError()
                    else -> errorMessageDataSource.getUnknownError()
                }
            return PresentationError(message, throwable)
        }

        private fun handleHttpException(exception: HttpException): String =
            if (exception.code() == AUTH_ERROR_HTTP_CODE) {
                errorMessageDataSource.getNotAuthError()
            } else {
                errorMessageDataSource.getUnknownError()
            }

        companion object {
            const val AUTH_ERROR_HTTP_CODE = 401
        }
    }