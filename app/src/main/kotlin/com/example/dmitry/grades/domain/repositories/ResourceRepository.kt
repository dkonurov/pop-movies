package com.example.dmitry.grades.domain.repositories

import android.content.Context
import com.example.dmitry.grades.R
import javax.inject.Inject

open class ResourceRepository @Inject constructor(private val context: Context) {

    open fun getNetworkError(): String {
        return context.getString(R.string.network_error)
    }

    fun getUnknownError(): String {
        return context.getString(R.string.unknown_error)
    }

    fun getNotAuthError(): String {
        return context.getString(R.string.non_auth_error)
    }
}