package com.example.mvvm.postsfavorites.feature.auth.data.usecase

import android.util.Patterns
import com.example.mvvm.postsfavorites.feature.auth.domain.usecase.ValidateEmailUseCase
import javax.inject.Inject

internal class ValidateEmailUseCaseImpl @Inject constructor() : ValidateEmailUseCase {
    override fun invoke(email: String): Boolean {
        if (email.isBlank()) return false
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
