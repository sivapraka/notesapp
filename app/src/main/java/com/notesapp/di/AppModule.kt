package com.notesapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.notesapp.data.local.NoteDatabase
import com.notesapp.data.remote.ApiService
import com.notesapp.data.remote.NetworkUtils
import com.notesapp.data.remote.api.MoviesApi
import com.notesapp.data.repository.MovieRepositoryImpl
import com.notesapp.data.repository.NoteRepositoryImpl
import com.notesapp.domain.repository.MovieRepository
import com.notesapp.domain.repository.NoteRepository
import com.notesapp.domain.usecase.AddNotesUseCase
import com.notesapp.domain.usecase.DeleteNotesUseCase
import com.notesapp.domain.usecase.GetMovieUseCase
import com.notesapp.domain.usecase.GetNotesUseCase
import com.notesapp.domain.usecase.NoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils {
        return NetworkUtils(context)
    }


    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase,apiService: ApiService,networkUtil: NetworkUtils): NoteRepository {
        return NoteRepositoryImpl(db.noteDao(),apiService,networkUtil)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            addNotesUseCase = AddNotesUseCase(repository),
            deleteNotesUseCase = DeleteNotesUseCase(repository),
            getNotesUseCase = GetNotesUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Add the new column with a default value
            database.execSQL("ALTER TABLE notes ADD COLUMN timestamp INTEGER NOT NULL DEFAULT 0")
        }
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MoviesApi): MovieRepository {
        return MovieRepositoryImpl(api, apiKey = "5127029d")
    }

    @Provides
    @Singleton
    fun provideGetMovieUseCase(repository: MovieRepository): GetMovieUseCase {
        return GetMovieUseCase(repository)
    }


}
