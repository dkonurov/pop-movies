package com.example.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.data.db.convertors.ListIntConverter
import com.example.core.data.db.inteface.FavoriteDao
import com.example.core.data.db.inteface.IDatabase
import com.example.core.data.db.inteface.MovieDao
import com.example.core.models.entity.Favorite
import com.example.core.models.entity.Movie

@Database(entities = [Movie::class, Favorite::class], version = 1, exportSchema = true)
@TypeConverters(ListIntConverter::class)
internal abstract class AppDatabase : RoomDatabase(), IDatabase {

    abstract override fun getMovieDao(): MovieDao

    abstract override fun getFavoriteDao(): FavoriteDao
}