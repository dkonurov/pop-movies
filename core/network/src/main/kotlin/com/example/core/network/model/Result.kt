package com.example.core.network.model

sealed class Result<T> {
    data class Success<T>(
        val value: T,
    ) : Result<T>()

    data class Failure<T>(
        val throwable: Throwable,
    ) : Result<T>()
}

inline fun <Input, Return> Input.runCatching(block: Input.() -> Return): Result<Return> =
    try {
        Result.Success(block.invoke(this))
    } catch (e: Throwable) {
        Result.Failure(e)
    }

fun <T> Result<T>.getOrThrow(): T =
    when (this) {
        is Result.Failure -> throw throwable
        is Result.Success -> value
    }

fun <R, T : R> Result<T>.getOrElse(onFailure: (exception: Throwable) -> R): R =
    when (this) {
        is Result.Success -> value
        is Result.Failure -> onFailure.invoke(throwable)
    }

fun <R, T : R> Result<T>.getOrDefault(defaultValue: R): R =
    when (this) {
        is Result.Success -> value
        is Result.Failure -> defaultValue
    }