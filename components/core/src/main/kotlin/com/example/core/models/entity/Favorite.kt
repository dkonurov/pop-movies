package com.example.core.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Favorite.TABLE_NAME)
data class Favorite(
    @PrimaryKey val id: Long,
    val title: String,
    val about: String,
    val poster: String?,
    val time: String?,
    val release: String
) {
    companion object {
        const val TABLE_NAME = "Favorite"
    }
}