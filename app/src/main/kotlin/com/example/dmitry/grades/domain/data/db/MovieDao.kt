package com.example.dmitry.grades.domain.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.dmitry.grades.domain.models.Movie
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(movies: List<Movie>)

    @Query("SELECT * FROM " + Movie.TABLE_NAME)
    fun getAll(): Single<List<Movie>>
}