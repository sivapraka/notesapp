package com.notesapp.util

import com.google.gson.annotations.SerializedName

data class ServerErrorResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("success") val success: Boolean
)