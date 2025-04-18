package com.example.core.data

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkUtils {
    private val NETWORK_EXCEPTIONS =
        listOf<Class<*>>(
            UnknownHostException::class.java,
            SocketTimeoutException::class.java,
            ConnectException::class.java,
        )

    fun isNetworkException(throwable: Throwable): Boolean = NETWORK_EXCEPTIONS.contains(throwable.javaClass)
}