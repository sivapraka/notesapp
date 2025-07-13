package com.notesapp.util

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

fun <T> safeApiCall(
    apiCall: suspend () -> T
): Flow<ApiResource<T>> = flow {
    val TAG = "SafeAPI Call"
    emit(ApiResource.Loading)
    try {
        val response = apiCall()
        emit(ApiResource.Success(response))
    } catch (e: HttpException) {
        Log.e(TAG, "safeApiCall: "+e.printStackTrace() )
        val error = parseServerError(e.response()?.errorBody())
        emit(ApiResource.Error(error.statusMessage, error.statusCode))
    } catch (e: IOException) {
        Log.e(TAG, "safeApiCall: "+e.printStackTrace() )
        emit(ApiResource.Error("Network Error. Check internet connection."))
    } catch (e: Exception) {
        Log.e(TAG, "safeApiCall: "+e.printStackTrace() )
        emit(ApiResource.Error("Unexpected error: ${e.localizedMessage}"))
    }
}