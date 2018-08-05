package com.example.dmitry.grades.domain

import android.util.Log

class Logger {

    fun d(tag: String, message: String) {
        Log.d(message, message)
    }

    fun error(throwable: Throwable) {
        Log.e(throwable.javaClass.simpleName, throwable.message)
    }
}