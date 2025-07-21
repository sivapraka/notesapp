package com.notesapp.di

import android.content.Context
import androidx.room.Room
import com.notesapp.data.local.Database
import com.notesapp.data.local.dao.ImageConfigDao
import com.notesapp.data.local.dao.ImdbMoviesDao
import com.notesapp.data.local.dao.PageDao
import com.notesapp.data.local.dao.KeyConfigDao
import com.notesapp.data.local.dao.LanguageDao
import com.notesapp.data.remote.ImdbApi
import com.notesapp.data.remote.api.ApiService
import com.notesapp.data.remote.api.MoviesApi
import com.notesapp.data.repository.ImdbConfigRepositoryImpl
import com.notesapp.data.repository.ImdbMovieRepositoryImpl
import com.notesapp.data.repository.MovieRepositoryImpl
import com.notesapp.data.repository.NoteRepositoryImpl
import com.notesapp.data.repository.TimezoneRepositoryImpl
import com.notesapp.domain.repository.ImdbConfigRepository
import com.notesapp.domain.repository.ImdbMovieRepository
import com.notesapp.domain.repository.MovieRepository
import com.notesapp.domain.repository.NoteRepository
import com.notesapp.domain.repository.TimezoneRepository
import com.notesapp.domain.usecase.AddNotesUseCase
import com.notesapp.domain.usecase.DeleteNotesUseCase
import com.notesapp.domain.usecase.GetMovieUseCase
import com.notesapp.domain.usecase.GetNotesUseCase
import com.notesapp.domain.usecase.GetTimezonesUseCase
import com.notesapp.domain.usecase.NoteUseCase
import com.notesapp.util.NetworkUtils
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
    fun provideNoteRepository(
        db: Database,
        apiService: ApiService,
        networkUtil: NetworkUtils
    ): NoteRepository {
        return NoteRepositoryImpl(db.noteDao(), apiService, networkUtil)
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
    fun provideNoteDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "note_db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideMovieRepository(db: Database, api: MoviesApi): MovieRepository {
        return MovieRepositoryImpl(db.movieDao(), api, apiKey = "5127029d")
    }

    @Provides
    @Singleton
    fun provideGetMovieUseCase(repository: MovieRepository): GetMovieUseCase {
        return GetMovieUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLanguageDao(db: Database): LanguageDao {
        return db.languageDao()
    }

    @Provides
    @Singleton
    fun provideTimeZoneRepository(db: Database, api: ImdbApi): TimezoneRepository {
        return TimezoneRepositoryImpl(db.timeZoneDao(), api)
    }

    @Provides
    @Singleton
    fun provideGetTimeZoneUseCase(repository: TimezoneRepository): GetTimezonesUseCase {
        return GetTimezonesUseCase(repository)
    }

    // DAO
    @Provides
    fun provideImdbMoviesDao(db: Database): ImdbMoviesDao {
        return db.imdbMoviesDao()
    }

    @Provides
    fun providePageDao(db: Database): PageDao{
        return db.pageDao()
    }

    // Repository
    @Provides
    @Singleton
    fun provideImdbRepository(dao: ImdbMoviesDao, pageDao: PageDao, api: ImdbApi,  networkUtil: NetworkUtils): ImdbMovieRepository {
        return ImdbMovieRepositoryImpl(moviesDao = dao,pageDao=pageDao, api = api,networkUtils=  networkUtil)
    }

    // DAO
    @Provides
    fun provideImageConfigMoviesDao(db: Database): ImageConfigDao {
        return db.imageConfigDao()
    }

    // DAO
    @Provides
    fun provideKeyConfigMoviesDao(db: Database): KeyConfigDao {
        return db.keyConfigDao()
    }
    // Repository
    @Provides
    @Singleton
    fun provideImdbImageConfigRepository(dao: ImageConfigDao,dao1: KeyConfigDao, api: ImdbApi): ImdbConfigRepository {
        return ImdbConfigRepositoryImpl(imageConfigDao=dao,keyConfigDao = dao1, api = api)
    }
}
