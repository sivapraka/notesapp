package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "change_keys")
data class ChangeKey(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val key: String

)