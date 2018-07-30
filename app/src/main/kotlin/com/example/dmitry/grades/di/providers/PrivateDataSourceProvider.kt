package com.example.dmitry.grades.di.providers

import android.content.Context
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSourceImpl
import javax.inject.Inject
import javax.inject.Provider

class PrivateDataSourceProvider @Inject constructor(private val context: Context) : Provider<PrivateDataSource> {
    override fun get(): PrivateDataSource {
        return PrivateDataSourceImpl(context)
    }
}