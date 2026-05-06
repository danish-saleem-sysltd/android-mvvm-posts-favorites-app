package com.example.mvvm.postsfavorites.feature.auth.data.usecase

import com.example.mvvm.postsfavorites.feature.auth.domain.usecase.ValidatePasswordUseCase
import javax.inject.Inject

internal class ValidatePasswordUseCaseImpl @Inject constructor() : ValidatePasswordUseCase {
    override fun invoke(password: String): Boolean {
        return password.length in 8..15
    }
}
