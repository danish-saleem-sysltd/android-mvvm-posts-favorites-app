package com.example.mvvm.postsfavorites.feature.auth.ui.screen.login.entity

import androidx.compose.runtime.Immutable

@Immutable
sealed class LoginUiEvent {
    data class EmailChanged(val value: String) : LoginUiEvent()
    data class PasswordChanged(val value: String) : LoginUiEvent()
    data object Submit : LoginUiEvent()
    data object NavigationHandled : LoginUiEvent()
}
