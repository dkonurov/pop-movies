package com.example.core.data.logger

import android.util.Log

internal class LoggerImpl : Logger {

    override fun debug(tag: String, message: String) {
        Log.d(message, message)
    }

    override fun error(throwable: Throwable) {
        Log.e(throwable.javaClass.simpleName, throwable.message)
    }
}