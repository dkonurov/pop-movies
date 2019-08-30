package com.example.core.data.message

interface ErrorMessageDataSource {

    fun getNetworkError(): String

    fun getUnknownError(): String

    fun getNotAuthError(): String
}