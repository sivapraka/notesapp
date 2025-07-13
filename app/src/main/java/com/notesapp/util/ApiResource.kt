package com.notesapp.util

sealed class ApiResource<out T> {
    object Loading : ApiResource<Nothing>()
    data class Success<T>(val data: T) : ApiResource<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResource<Nothing>()
}