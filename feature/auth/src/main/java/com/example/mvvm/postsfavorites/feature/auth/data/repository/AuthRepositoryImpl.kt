package com.example.mvvm.postsfavorites.feature.auth.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.ReadAuthRepository
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.WriteAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ReadAuthRepository, WriteAuthRepository {

    override fun observeIsLoggedIn(): Flow<Boolean> =
        dataStore.data
            .map { prefs -> !prefs[EMAIL_KEY].isNullOrBlank() }
            .onEach { Log.d(TAG, "observeIsLoggedIn emit: $it") }

    override fun observeEmail(): Flow<String?> =
        dataStore.data.map { prefs -> prefs[EMAIL_KEY] }

    override suspend fun saveSession(email: String) {
        Log.d(TAG, "saveSession: email=$email")
        dataStore.edit { prefs -> prefs[EMAIL_KEY] = email }
    }

    override suspend fun clearSession() {
        Log.d(TAG, "clearSession")
        dataStore.edit { prefs -> prefs.remove(EMAIL_KEY) }
    }

    private companion object {
        val EMAIL_KEY = stringPreferencesKey("auth_session_email")
        const val TAG = "Auth.Repo"
    }
}
