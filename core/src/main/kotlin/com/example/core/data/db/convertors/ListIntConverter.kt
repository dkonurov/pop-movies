package com.example.core.data.db.convertors

import androidx.room.TypeConverter
import com.example.core.containers.ContainerDI
import com.google.gson.reflect.TypeToken

internal class ListIntConverter {

    @TypeConverter
    fun convertFromListInt(list: List<Int>): String {
        val gson = ContainerDI.gson
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertToListInt(json: String): List<Int> {
        val gson = ContainerDI.gson
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type)
    }
}