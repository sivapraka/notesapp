package com.notesapp.data.mapper

import com.notesapp.data.local.entity.MovieWithRatings
import com.notesapp.data.local.entity.Movies
import com.notesapp.data.local.entity.Rating
import com.notesapp.data.remote.MovieResponse
import com.notesapp.data.remote.RatingDto


fun MovieResponse.toMovieEntity(): Movies {
    return Movies(
        imdbID = imdbID,
        title = Title,
        year = Year,
        rated = Rated,
        released = Released,
        runtime = Runtime,
        genre = Genre,
        director = Director,
        writer = Writer,
        actors = Actors,
        plot = Plot,
        language = Language,
        country = Country,
        awards = Awards,
        poster = Poster,
        metascore = Metascore,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        type = Type,
        boxOffice = BoxOffice,
        production = Production,
        website = Website,
        response = Response
    )
}

fun MovieResponse.toRatingEntity(): List<Rating> {
    return Ratings.map {
        Rating(
            source = it.Source,
            value = it.Value,
            movieId = imdbID
        )
    }
}

fun MovieWithRatings.toMovie(): MovieResponse {
    return MovieResponse(
        Year = movie.year ?: "",
        Title = movie.title ?: "",
        Rated = movie.rated ?: "",
        Released = movie.released ?: "",
        Runtime = movie.runtime ?: "",
        Genre = movie.genre ?: "",
        Director = movie.director ?: "",
        Writer = movie.writer ?: "",
        Actors = movie.actors ?: "",
        Plot = movie.plot ?: "",
        Language = movie.language ?: "",
        Country = movie.country ?: "",
        Awards = movie.awards ?: "",
        Poster = movie.poster ?: "",
        Ratings = ratings.map { RatingDto(it.source, it.value) },
        Metascore = movie.metascore ?: "",
        imdbRating = movie.imdbRating ?: "",
        imdbVotes = movie.imdbVotes ?: "",
        imdbID = movie.imdbID ?: "",
        Type = movie.type ?: "",
        BoxOffice = movie.boxOffice ?: "",
        Production = movie.production ?: "",
        Website = movie.website ?: "",
        Response = movie.response ?: ""
    )
}
