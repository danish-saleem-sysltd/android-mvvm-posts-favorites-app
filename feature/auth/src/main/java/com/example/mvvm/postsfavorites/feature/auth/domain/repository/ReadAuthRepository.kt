package com.example.mvvm.postsfavorites.feature.auth.domain.repository

import kotlinx.coroutines.flow.Flow

interface ReadAuthRepository {
    fun observeIsLoggedIn(): Flow<Boolean>
    fun observeEmail(): Flow<String?>
}
