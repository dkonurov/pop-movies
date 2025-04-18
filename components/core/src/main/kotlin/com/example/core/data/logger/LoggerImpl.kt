package com.example.core.data.logger

import android.util.Log
import javax.inject.Inject

internal class LoggerImpl
    @Inject
    constructor() : Logger {
        override fun debug(
            tag: String,
            message: String,
        ) {
            Log.d(message, message)
        }

        override fun error(
            throwable: Throwable,
            message: String?,
        ) {
            Log.e(throwable.javaClass.simpleName, message ?: throwable.message, throwable)
        }
    }