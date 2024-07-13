package com.example.jwtauthktorapp

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException


class TokenPreferencesRepositoryImpl(private val context: Context) : TokenPreferencesRepository {
     companion object {
        private const val TOKEN_PREFERENCE_NAME = "token_preferences"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = TOKEN_PREFERENCE_NAME
        )
        const val TAG = "TokenPreferencesRepo"
        val JWT_TOKEN = stringPreferencesKey("jwt")
    }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = token
        }
    }

    private val getToken: Flow<String> = context.dataStore.data.catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[JWT_TOKEN] ?: ""
        }

    override suspend fun getTokenValue(): String {
        return getToken.first()
    }


}