package com.notesapp.domain.model

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    @SerializedName("success") var success: Boolean = false,
    @SerializedName("stauts_code") var stauts_code: Int = 0,
    @SerializedName("status_message") var status_message: String = "",
    @SerializedName("request_token") var request_token: String = ""
)