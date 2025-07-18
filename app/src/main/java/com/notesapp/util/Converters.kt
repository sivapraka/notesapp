package com.notesapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromGenreIdList(list: List<Int>?): String  {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toGenreIdList(json: String): List<Int>? {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(json, type)
    }
}