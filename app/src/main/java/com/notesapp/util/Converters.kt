package com.notesapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.notesapp.data.local.entity.ImdbCollections
import com.notesapp.data.local.entity.ImdbGenres
import com.notesapp.data.local.entity.ImdbProductionCompanies
import com.notesapp.data.local.entity.ImdbProductionCountries
import com.notesapp.data.local.entity.ImdbSpokenLanguage

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

    @TypeConverter
    fun fromGenresList(genres: List<ImdbGenres>): String = Gson().toJson(genres)

    @TypeConverter
    fun toGenresList(json: String): List<ImdbGenres> =
        Gson().fromJson(json, object : TypeToken<List<ImdbGenres>>() {}.type)


    @TypeConverter
    fun fromCollection(value: ImdbCollections): String = Gson().toJson(value)

    @TypeConverter
    fun toCollection(value: String): ImdbCollections =
        Gson().fromJson(value, object : TypeToken<ImdbCollections>() {}.type)

    @TypeConverter
    fun fromProductionCompanies(value: List<ImdbProductionCompanies>): String = Gson().toJson(value)

    @TypeConverter
    fun toProductionCompanies(value: String): List<ImdbProductionCompanies> =
        Gson().fromJson(value, object : TypeToken<List<ImdbProductionCompanies>>() {}.type)

    @TypeConverter
    fun fromProductionCountries(value: List<ImdbProductionCountries>): String = Gson().toJson(value)

    @TypeConverter
    fun toProductionCountries(value: String): List<ImdbProductionCountries> =
        Gson().fromJson(value, object : TypeToken<List<ImdbProductionCountries>>() {}.type)

    @TypeConverter
    fun fromSpokenLanguages(value: List<ImdbSpokenLanguage>): String = Gson().toJson(value)

    @TypeConverter
    fun toSpokenLanguages(value: String): List<ImdbSpokenLanguage> =
        Gson().fromJson(value, object : TypeToken<List<ImdbSpokenLanguage>>() {}.type)

}