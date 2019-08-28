package com.example.dmitry.grades.di.providers

import android.content.Context
import com.example.core.containers.createPrivateDataSource
import com.example.core.data.preferences.PrivateDataSource
import javax.inject.Inject
import javax.inject.Provider

class PrivateDataSourceProvider @Inject constructor(private val context: Context) :
    Provider<PrivateDataSource> {
    override fun get(): PrivateDataSource = createPrivateDataSource(context)
}