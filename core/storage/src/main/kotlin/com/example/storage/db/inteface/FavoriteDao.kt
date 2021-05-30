package com.example.storage.db.inteface

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storage.db.entity.LocalFavorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(favorite: LocalFavorite)

    @Delete
    fun delete(favorite: LocalFavorite)

    @Query("DELETE FROM ${LocalFavorite.TABLE_NAME} WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM ${LocalFavorite.TABLE_NAME} WHERE id = :id")
    fun findById(id: Long): LocalFavorite?

    @Query("SELECT id FROM ${LocalFavorite.TABLE_NAME} LIMIT :offset, :limit")
    fun getMoviesId(offset: Int, limit: Int): List<Long>

    @Query("SELECT COUNT(id) FROM ${LocalFavorite.TABLE_NAME}")
    fun count(): Int
}