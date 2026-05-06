package com.example.mvvm.postsfavorites.feature.auth.domain.usecase

interface ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean
}
