package com.example.core.storage.preferences

interface ErrorMessageDataSource {

    fun getNetworkError(): String

    fun getUnknownError(): String

    fun getNotAuthError(): String
}