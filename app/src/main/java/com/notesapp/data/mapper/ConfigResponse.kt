package com.notesapp.data.mapper

import com.google.gson.annotations.SerializedName
import com.notesapp.data.local.entity.ChangeKey
import com.notesapp.data.local.entity.ImageConfigEntity

data class ConfigResponse(
    @SerializedName("change_keys") val changeKeys: List<String>,
    @SerializedName("images") val images: ImageConfigEntity)

fun List<String>.toChangeKey(): List<ChangeKey> {
    return map { ChangeKey(key = it) }
}