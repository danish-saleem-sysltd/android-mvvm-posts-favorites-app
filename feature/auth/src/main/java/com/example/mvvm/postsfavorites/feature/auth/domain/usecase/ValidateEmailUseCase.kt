package com.example.mvvm.postsfavorites.feature.auth.domain.usecase

interface ValidateEmailUseCase {
    operator fun invoke(email: String): Boolean
}
