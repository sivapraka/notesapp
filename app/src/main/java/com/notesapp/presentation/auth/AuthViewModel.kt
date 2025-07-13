package com.notesapp.presentation.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.usecase.GetRequestTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getRequestTokenUseCase: GetRequestTokenUseCase
) : ViewModel() {

    private val _tokenState = mutableStateOf<String?>(null)
    fun fetchToken() {
        viewModelScope.launch {
            try {
                val tokenResponse = getRequestTokenUseCase()
                _tokenState.value = tokenResponse.request_token
            } catch (e: Exception) {
                Log.e("Auth", "Error fetching token", e)
            }
        }
    }
}