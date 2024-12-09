package com.example.nutrisense.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    // Menyimpan token dan username
    suspend fun saveAccessToken(token: String, userName: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
            preferences[USER_NAME_KEY] = userName
        }
    }

    // Mendapatkan token yang disimpan (langsung, bukan Flow)
    suspend fun getAccessToken(): String {
        val preferences = dataStore.data.first()
        return preferences[ACCESS_TOKEN_KEY] ?: ""
    }

    // Mendapatkan username yang disimpan (langsung, bukan Flow)
    suspend fun getUserName(): String {
        val preferences = dataStore.data.first()
        return preferences[USER_NAME_KEY] ?: ""
    }

    // Menghapus data user (token dan username)
    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(USER_NAME_KEY)
        }
    }
}
