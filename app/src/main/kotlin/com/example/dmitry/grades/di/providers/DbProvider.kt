package com.example.dmitry.grades.di.providers

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.dmitry.grades.di.DbName
import com.example.dmitry.grades.domain.data.db.AppDatabase
import javax.inject.Inject
import javax.inject.Provider

class DbProvider @Inject constructor(private val context: Context,
                                     @DbName private val dbName: String) : Provider<AppDatabase> {
    override fun get(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
    }
}