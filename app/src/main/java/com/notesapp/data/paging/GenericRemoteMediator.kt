package com.notesapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.log


@OptIn(ExperimentalPagingApi::class)
class GenericRemoteMediator<T : Any>(
    private val fetchFromRemote: suspend (page: Int) -> List<T>,
    private val clearDb: suspend () -> Unit,
    private val insertToDb: suspend (List<T>) -> Unit
) : RemoteMediator<Int, T>() {

    private var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 1
                    clearDb()
                    currentPage
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> currentPage
            }

            val remoteData = fetchFromRemote(page)

            insertToDb(remoteData)

            val endReached = remoteData.isEmpty() || remoteData.size < state.config.pageSize
            if (!endReached) currentPage++ // Increment page only if more pages exist

            MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
