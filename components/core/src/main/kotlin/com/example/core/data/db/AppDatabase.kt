package com.example.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.data.db.convertors.ListIntConverter
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.db.inteface.MovieDao
import com.example.core.models.entity.LocalFavorite
import com.example.core.models.entity.LocalMovie

@Database(entities = [LocalMovie::class, LocalFavorite::class], version = 1, exportSchema = true)
@TypeConverters(ListIntConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    abstract fun getFavoriteDao(): FavoriteDao
}