package com.example.base.extensions

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

@Suppress("NOTHING_TO_INLINE")
suspend inline fun <T> Call<T>.await(): T = suspendCancellableCoroutine {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            it.resumeWith(Result.failure(t))
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful)
                it.resumeWith(Result.success(response.body()!!))
            else
                it.resumeWith(Result.failure(IOException(response.message())))
        }
    })
}