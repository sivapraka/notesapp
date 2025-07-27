package com.notesapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.notesapp.data.local.dao.PageDao
import com.notesapp.data.local.entity.PagesEntity
import com.notesapp.data.model.ImdbMovieResponse
import com.notesapp.util.NetworkUtils


@OptIn(ExperimentalPagingApi::class)
class GenericRemoteMediator<T : Any>(
    private val networkUtils: NetworkUtils,
    private val fetchFromRemote: suspend (page: Int) -> ImdbMovieResponse,
    private val clearDb: suspend () -> Unit,
    private val insertToDb: suspend (List<T>) -> Unit,
    private val remoteKeysDao: PageDao,
    private val label: String
) : RemoteMediator<Int, T>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>
    ): MediatorResult {
        return try {
            if (!networkUtils.isNetworkAvailable()) {
                // No internet → Skip fetch and just use DB
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            val remoteKey = remoteKeysDao.getRemoteKey(label)
            val page = when (loadType) {
                LoadType.REFRESH -> {
                   val isDbEmpty = remoteKeysDao.count() == 0
                    if (!isDbEmpty) {
                        return MediatorResult.Success(endOfPaginationReached = false)
                    }else {
                        remoteKeysDao.getRemoteKey(label)
                        clearDb()
                        1
                    }
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val nextPage = (remoteKey?.page ?: 1) + 1
                    if (remoteKey != null && nextPage > remoteKey.total_pages) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    nextPage
                }
            }

            // Fetch and store
            val response = fetchFromRemote(page)
            val results = response.results as List<T>
            insertToDb(results)
            // Save latest page and total
            remoteKeysDao.insertKeys(
                PagesEntity(
                    page = page,
                    total_pages = response.total_pages,
                    total_results = response.total_results,
                    label = label
                )
            )
            return MediatorResult.Success(endOfPaginationReached = page >= response.total_pages)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
