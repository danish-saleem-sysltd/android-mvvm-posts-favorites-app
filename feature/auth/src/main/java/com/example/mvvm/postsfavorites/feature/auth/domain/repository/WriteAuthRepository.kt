package com.example.mvvm.postsfavorites.feature.auth.domain.repository

interface WriteAuthRepository {
    suspend fun saveSession(email: String)
    suspend fun clearSession()
}
