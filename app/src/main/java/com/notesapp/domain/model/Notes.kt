package com.notesapp.domain.model

data class Notes(
    var id: Int = 0,
    var title: String, var description: String,
    var date: String= System.currentTimeMillis().toString(),
    var upload: String="N"
)
