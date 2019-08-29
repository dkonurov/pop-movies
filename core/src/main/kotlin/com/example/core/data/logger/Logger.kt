package com.example.core.data.logger

interface Logger {

    fun debug(tag: String, message: String)

    fun error(throwable: Throwable)
}