package com.example.core.data.db.inteface

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.models.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("DELETE FROM ${Favorite.TABLE_NAME} WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM ${Favorite.TABLE_NAME} WHERE id = :id")
    fun findById(id: Long): Favorite?

    @Query("SELECT id FROM ${Favorite.TABLE_NAME} LIMIT :offset, :limit")
    fun getMoviesId(offset: Int, limit: Int): List<Long>

    @Query("SELECT COUNT(id) FROM ${Favorite.TABLE_NAME}")
    fun count(): Int
}