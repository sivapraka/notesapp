package com.notesapp.domain.usecase

import com.notesapp.data.local.entity.ImageConfigEntity
import com.notesapp.domain.repository.ImdbConfigRepository
import com.notesapp.util.ApiResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetImdbImageConfigUseCase @Inject constructor(
    private val repository: ImdbConfigRepository
) {
    operator fun invoke(): Flow<ApiResource<ImageConfigEntity?>> = flow{
        emit(ApiResource.Loading)
        try {
            repository.downloadConfig().collect {
                emit(ApiResource.Success(it))
            }
        }catch (e:Exception){
            Timber.e(e.message.toString())
            emit(ApiResource.Error(e.message.toString()))
        }

    }
}
