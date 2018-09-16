package com.example.dmitry.grades.domain.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.dmitry.grades.domain.models.entity.Favorite

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