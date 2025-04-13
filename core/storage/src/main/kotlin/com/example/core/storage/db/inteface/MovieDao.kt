package com.example.core.storage.db.inteface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.storage.db.entity.LocalMovie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(movies: List<LocalMovie>)

    @Query("SELECT * FROM ${LocalMovie.TABLE_NAME} WHERE page = :page")
    fun getMovies(page: Int): List<LocalMovie>

    @Query("DELETE FROM ${LocalMovie.TABLE_NAME}")
    fun clear()
}