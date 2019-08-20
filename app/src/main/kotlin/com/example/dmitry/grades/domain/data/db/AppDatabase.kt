package com.example.dmitry.grades.domain.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dmitry.grades.domain.data.db.convertors.ListIntConverter
import com.example.dmitry.grades.domain.models.entity.Favorite
import com.example.dmitry.grades.domain.models.entity.Movie

@Database(entities = [Movie::class, Favorite::class], version = 1, exportSchema = true)
@TypeConverters(ListIntConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    abstract fun getFavoriteDao(): FavoriteDao
}