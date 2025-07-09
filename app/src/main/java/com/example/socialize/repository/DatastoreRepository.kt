package com.example.socialize.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatastoreRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_USERNAME = stringPreferencesKey("user_username")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_AGE = stringPreferencesKey("user_age")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NUMBER = stringPreferencesKey("user_number")
        val SESSION_TOKEN = stringPreferencesKey("session_token")
    }


    // --- Flows ---
    val userNameFlow: Flow<String> = getStringFlow(USER_NAME)
    val userUsernameFlow: Flow<String> = getStringFlow(USER_USERNAME)
    val userPasswordFlow: Flow<String> = getStringFlow(USER_PASSWORD)
    val userAgeFlow: Flow<String> = getStringFlow(USER_AGE)
    val userEmailFlow: Flow<String> = getStringFlow(USER_EMAIL)
    val userNumberFlow: Flow<String> = getStringFlow(USER_NUMBER)
    val sessionToken: Flow<String> = getStringFlow(SESSION_TOKEN)

    // --- Save functions ---
    suspend fun saveUserName(name: String) = saveString(USER_NAME, name)
    suspend fun saveUserUsername(username: String) = saveString(USER_USERNAME, username)
    suspend fun saveUserPassword(password: String) = saveString(USER_PASSWORD, password)
    suspend fun saveUserAge(age: String) = saveString(USER_AGE, age)
    suspend fun saveUserEmail(email: String) = saveString(USER_EMAIL, email)
    suspend fun saveUserNumber(number: String) = saveString(USER_NUMBER, number)
    suspend fun saveToken(token:String) = saveString(SESSION_TOKEN,token)

    // --- Helper functions ---
    fun getStringFlow(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: ""
        }
    }

    suspend fun saveString(key: Preferences.Key<String>, value: String) {

            dataStore.edit { preferences ->
                preferences[key] = value
            }

    }
}