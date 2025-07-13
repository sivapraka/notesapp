package com.notesapp.util


import com.google.gson.Gson
import okhttp3.ResponseBody

fun parseServerError(errorBody: ResponseBody?): ServerErrorResponse {
    return try {
        Gson().fromJson(errorBody?.charStream(), ServerErrorResponse::class.java)
    } catch (e: Exception) {
        ServerErrorResponse(-1, "Unknown error occurred", false)
    }
}