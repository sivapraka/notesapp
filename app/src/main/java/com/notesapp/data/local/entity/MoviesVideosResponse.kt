package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "videos_response")
data class MoviesVideosResponse(
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("results") val results: List<MoviesVideos?>,
 )