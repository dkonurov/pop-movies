package com.example.dmitry.grades.domain.data.db.convertors

import android.arch.persistence.room.TypeConverter
import com.example.dmitry.grades.di.Scopes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import toothpick.Toothpick

class ListIntConverter {

    @TypeConverter
    fun convertFromListInt(list: List<Int>): String {
        val gson = Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(Gson::class.java)
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertToListInt(json: String): List<Int> {
        val gson = Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(Gson::class.java)
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type)
    }
}