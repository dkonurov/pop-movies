package com.example.dmitry.grades.domain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dmitry.grades.domain.models.entity.Movie
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(movies: List<Movie>)

    @Query("SELECT * FROM ${Movie.TABLE_NAME} LIMIT :offset, :limit")
    fun getMovies(offset: Int, limit: Int): MutableList<Movie>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE localId = :id")
    fun findById(id: Int): Single<Movie>

    @Query("DELETE FROM ${Movie.TABLE_NAME}")
    fun clear()
}