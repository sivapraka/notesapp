package com.notesapp.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.notesapp.data.local.dao.ImdbMoviesDao
import com.notesapp.data.local.entity.ImdbMovies
import com.notesapp.data.paging.GenericRemoteMediator
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.ImdbMovieRepository
import kotlinx.coroutines.flow.Flow

class ImdbMovieRepositoryImpl(
    private val moviesDao: ImdbMoviesDao,
    private val api: ImdbApi,
) : ImdbMovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMovie(language: String): Flow<PagingData<ImdbMovies>> {

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = GenericRemoteMediator(
                fetchFromRemote = { page ->
                    api.discoverMovies(language = language, page = page).results
                },
                clearDb = {
                    moviesDao.clearMovies()
                },
                insertToDb = { movies ->
                      moviesDao.insertMovie(movies)
                }
            ),
            pagingSourceFactory = { moviesDao.getMovies() }
        ).flow

    }
}