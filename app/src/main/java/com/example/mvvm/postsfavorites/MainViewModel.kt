package com.example.mvvm.postsfavorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.ReadAuthRepository
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.WriteAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SessionUiState {
    data object Loading : SessionUiState
    data object LoggedOut : SessionUiState
    data object LoggedIn : SessionUiState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    readAuthRepository: ReadAuthRepository,
    private val writeAuthRepository: WriteAuthRepository,
) : ViewModel() {

    val sessionState: StateFlow<SessionUiState> = readAuthRepository.observeIsLoggedIn()
        .map<Boolean, SessionUiState> { logged ->
            if (logged) SessionUiState.LoggedIn else SessionUiState.LoggedOut
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = SessionUiState.Loading,
        )

    fun logout() {
        viewModelScope.launch { writeAuthRepository.clearSession() }
    }
}
