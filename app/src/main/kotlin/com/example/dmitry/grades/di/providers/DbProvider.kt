package com.example.dmitry.grades.di.providers

import android.content.Context
import com.example.core.containers.createDatabase
import com.example.core.data.db.inteface.IDatabase
import com.example.dmitry.grades.di.DbName
import javax.inject.Inject
import javax.inject.Provider

class DbProvider @Inject constructor(
    private val context: Context,
    @DbName private val dbName: String
) : Provider<IDatabase> {
    override fun get(): IDatabase {
        return createDatabase(context, dbName)
    }
}