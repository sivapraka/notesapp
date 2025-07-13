package com.notesapp.domain.usecase

import com.notesapp.domain.model.RequestTokenResponse
import com.notesapp.domain.repository.LanguageRepository
import javax.inject.Inject

class GetRequestTokenUseCase @Inject constructor(private val repository: LanguageRepository) {
    suspend operator fun invoke(): RequestTokenResponse {
        return repository.getRequestToken()
    }
}