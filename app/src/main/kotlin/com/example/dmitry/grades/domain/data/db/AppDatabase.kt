package com.example.dmitry.grades.domain.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.dmitry.grades.domain.data.db.convertors.ListIntConverter
import com.example.dmitry.grades.domain.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = true)
@TypeConverters(ListIntConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
}