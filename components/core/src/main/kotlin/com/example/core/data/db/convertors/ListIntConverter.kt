package com.example.core.data.db.convertors

import androidx.room.TypeConverter
import com.example.core.di.CoreScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class ListIntConverter {

    @TypeConverter
    fun convertFromListInt(list: List<Int>): String {
        val gson = CoreScope.getCoreScope().getInstance(Gson::class.java)
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertToListInt(json: String): List<Int> {
        val gson = CoreScope.getCoreScope().getInstance(Gson::class.java)
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type)
    }
}