package com.example.mvvm.postsfavorites.feature.auth.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.ReadAuthRepository
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.WriteAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ReadAuthRepository, WriteAuthRepository {

    override fun observeIsLoggedIn(): Flow<Boolean> =
        dataStore.data.map { prefs -> !prefs[EMAIL_KEY].isNullOrBlank() }

    override fun observeEmail(): Flow<String?> =
        dataStore.data.map { prefs -> prefs[EMAIL_KEY] }

    override suspend fun saveSession(email: String) {
        dataStore.edit { prefs -> prefs[EMAIL_KEY] = email }
    }

    override suspend fun clearSession() {
        dataStore.edit { prefs -> prefs.remove(EMAIL_KEY) }
    }

    private companion object {
        val EMAIL_KEY = stringPreferencesKey("auth_session_email")
    }
}
