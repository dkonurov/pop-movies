package com.example.core.storage.db.convertors

import androidx.room.TypeConverter

internal class ListIntConverter {
    @TypeConverter
    fun convertFromListInt(list: List<Int>): String = list.joinToString(",")

    @TypeConverter
    fun convertToListInt(json: String): List<Int> = json.split(",").map { it.toInt() }.toList()
}