package com.example.storage.preferences

interface ErrorMessageDataSource {

    fun getNetworkError(): String

    fun getUnknownError(): String

    fun getNotAuthError(): String
}