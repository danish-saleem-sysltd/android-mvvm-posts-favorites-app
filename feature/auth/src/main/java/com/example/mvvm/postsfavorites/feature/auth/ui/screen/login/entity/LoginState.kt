package com.example.mvvm.postsfavorites.feature.auth.ui.screen.login.entity

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailHasError: Boolean = false,
    val passwordHasError: Boolean = false,
    val isSubmitEnabled: Boolean = false,
    val loginSuccess: Boolean = false,
)
