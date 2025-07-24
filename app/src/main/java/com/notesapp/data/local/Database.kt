package com.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.notesapp.data.local.dao.ImageConfigDao
import com.notesapp.data.local.dao.ImdbMoviesDao
import com.notesapp.data.local.dao.ImdbMoviesDetailsDao
import com.notesapp.data.local.dao.PageDao
import com.notesapp.data.local.dao.KeyConfigDao
import com.notesapp.data.local.dao.LanguageDao
import com.notesapp.data.local.dao.MovieVideosDao
import com.notesapp.data.local.dao.MoviesDao
import com.notesapp.data.local.dao.NoteDao
import com.notesapp.data.local.dao.TimeZoneDao
import com.notesapp.data.local.entity.ChangeKey
import com.notesapp.data.local.entity.ImageConfigEntity
import com.notesapp.data.local.entity.ImdbCollections
import com.notesapp.data.local.entity.ImdbGenres
import com.notesapp.data.local.entity.PagesEntity
import com.notesapp.data.local.entity.ImdbMovies
import com.notesapp.data.local.entity.ImdbMoviesDetails
import com.notesapp.data.local.entity.ImdbProductionCompanies
import com.notesapp.data.local.entity.ImdbProductionCountries
import com.notesapp.data.local.entity.ImdbSpokenLanguage
import com.notesapp.data.local.entity.LanguageEntity
import com.notesapp.data.local.entity.Movies
import com.notesapp.data.local.entity.MoviesGenreId
import com.notesapp.data.local.entity.MoviesVideos
import com.notesapp.data.local.entity.MoviesVideosResponse
import com.notesapp.data.local.entity.Note
import com.notesapp.data.local.entity.Rating
import com.notesapp.data.local.entity.TimeZoneEntity
import com.notesapp.util.Converters


@Database(
    entities = [
        Note::class, Movies::class, Rating::class, LanguageEntity::class, TimeZoneEntity::class,
        ImdbMovies::class, MoviesGenreId::class, ChangeKey::class, ImageConfigEntity::class,
        PagesEntity::class, ImdbMoviesDetails::class, ImdbCollections::class,
        ImdbGenres::class, ImdbProductionCompanies::class, ImdbProductionCountries::class,
        ImdbSpokenLanguage::class, MoviesVideos::class, MoviesVideosResponse::class
    ], version = 9
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun movieDao(): MoviesDao
    abstract fun languageDao(): LanguageDao
    abstract fun timeZoneDao(): TimeZoneDao
    abstract fun imdbMoviesDao(): ImdbMoviesDao
    abstract fun keyConfigDao(): KeyConfigDao
    abstract fun imageConfigDao(): ImageConfigDao
    abstract fun pageDao(): PageDao
    abstract fun imdbMoviesDetailsDao(): ImdbMoviesDetailsDao
    abstract fun movieVideosDao(): MovieVideosDao

}