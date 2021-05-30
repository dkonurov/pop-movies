package com.example.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.storage.db.convertors.ListIntConverter
import com.example.storage.db.entity.LocalFavorite
import com.example.storage.db.entity.LocalMovie
import com.example.storage.db.inteface.FavoriteDao
import com.example.storage.db.inteface.MovieDao

@Database(entities = [LocalMovie::class, LocalFavorite::class], version = 1, exportSchema = true)
@TypeConverters(ListIntConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    abstract fun getFavoriteDao(): FavoriteDao
}