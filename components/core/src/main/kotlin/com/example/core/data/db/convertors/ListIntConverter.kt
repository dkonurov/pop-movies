package com.example.core.data.db.convertors

import androidx.room.TypeConverter

internal class ListIntConverter {

    @TypeConverter
    fun convertFromListInt(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun convertToListInt(json: String): List<Int> {
        return json.split(",").map { it.toInt() }.toList()
    }
}