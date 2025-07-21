package com.notesapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.notesapp.data.local.dao.ImdbMoviesDao
import com.notesapp.data.local.dao.PageDao
import com.notesapp.data.local.entity.ImdbMovies
import com.notesapp.data.paging.GenericRemoteMediator
import com.notesapp.data.remote.ImdbApi
import com.notesapp.domain.repository.ImdbMovieRepository
import com.notesapp.util.NetworkUtils
import kotlinx.coroutines.flow.Flow

class ImdbMovieRepositoryImpl(
    private val moviesDao: ImdbMoviesDao,
    private val pageDao: PageDao,
    private val api: ImdbApi,
    private val networkUtils: NetworkUtils
) : ImdbMovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMovie(language: String): Flow<PagingData<ImdbMovies>> {
        val type="movies"
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,   // instead of 60
                prefetchDistance = 5    // Optional, reduce preloading
            ),
            remoteMediator = GenericRemoteMediator(
                networkUtils=networkUtils,
                fetchFromRemote = { page -> api.discoverMovies(language = language, page = page)
                },
                clearDb = {
                    moviesDao.clearMovies()
                    pageDao.clearKeys(type)
                },
                insertToDb = { movies ->
                      moviesDao.insertMovie(movies)
                },
                pageDao,type
            ),
            pagingSourceFactory = { moviesDao.getMovies() }
        ).flow

    }
}