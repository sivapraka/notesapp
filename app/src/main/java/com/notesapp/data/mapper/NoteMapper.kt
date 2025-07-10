package com.notesapp.data.mapper

import com.notesapp.data.local.entity.Note
import com.notesapp.domain.model.Notes

// Convert Room entity to domain model
fun Note.toDomain(): Notes {
    return Notes(
        id = id,
        title = title.toString(),
        description = description.toString(),
        date = date,
        upload = upload.toString()
    )
}

// Convert domain model to Room entity
fun Notes.toEntity(): Note {
    return Note(
        id = id, title = title, description = description, date = date,
        upload = upload
    )
}