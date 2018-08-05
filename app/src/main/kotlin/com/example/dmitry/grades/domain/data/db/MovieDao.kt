package com.example.dmitry.grades.domain.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.dmitry.grades.domain.models.entity.Movie
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(movies: List<Movie>)

    @Query("SELECT * FROM ${Movie.TABLE_NAME} LIMIT :offset, :limit")
    fun getMovies(offset: Int, limit: Int): Single<MutableList<Movie>>

    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun getAll(): Single<List<Movie>>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE localId = :id")
    fun findById(id: Int): Single<Movie>

    @Query("DELETE FROM ${Movie.TABLE_NAME}")
    fun clear()
}