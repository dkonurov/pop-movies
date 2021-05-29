package com.example.core.data.message

import android.content.Context
import com.example.core.R
import javax.inject.Inject

internal class ErrorMessageDataSourceImpl @Inject constructor(private val context: Context) : ErrorMessageDataSource {

    override fun getNetworkError(): String {
        return context.getString(R.string.network_error)
    }

    override fun getUnknownError(): String {
        return context.getString(R.string.unknown_error)
    }

    override fun getNotAuthError(): String {
        return context.getString(R.string.non_auth_error)
    }
}