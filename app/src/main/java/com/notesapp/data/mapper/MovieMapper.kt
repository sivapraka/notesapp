package com.notesapp.data.mapper

import com.notesapp.data.remote.dto.MovieDto
import com.notesapp.domain.model.Movie


fun MovieDto.toDomain(): Movie {
    return Movie(
        title = Title,
        year = Year,
        genre = Genre,
        director = Director,
        plot = Plot,
        poster = Poster
    )
}

// Convert domain model to Room entity
fun Movie.toEntity(): MovieDto {
    return MovieDto(
        Title = title,
        Year = year,
        Genre = genre,
        Director = director,
        Plot = plot,
        Poster = poster
    )
}
