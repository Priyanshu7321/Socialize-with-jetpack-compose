package com.example.socialize.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        val SESSION_ACCEPT_TOKEN = stringPreferencesKey("accept_token")
        val SESSION_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val AUTHENTICATED = booleanPreferencesKey("authenticated")
    }


    // --- Flows ---
    val userNameFlow: Flow<String> = getStringFlow(USER_NAME)
    val userUsernameFlow: Flow<String> = getStringFlow(USER_USERNAME)
    val userPasswordFlow: Flow<String> = getStringFlow(USER_PASSWORD)
    val userAgeFlow: Flow<String> = getStringFlow(USER_AGE)
    val userEmailFlow: Flow<String> = getStringFlow(USER_EMAIL)
    val userNumberFlow: Flow<String> = getStringFlow(USER_NUMBER)
    val sessionAcceptToken: Flow<String> = getStringFlow(SESSION_ACCEPT_TOKEN)
    val sessionRefreshToken: Flow<String> = getStringFlow(SESSION_REFRESH_TOKEN)

    val authenticated: Flow<Boolean> = getBoolFlow(AUTHENTICATED)

    // --- Save functions ---
    suspend fun saveUserName(name: String) = saveString(USER_NAME, name)
    suspend fun saveUserUsername(username: String) = saveString(USER_USERNAME, username)
    suspend fun saveUserPassword(password: String) = saveString(USER_PASSWORD, password)
    suspend fun saveUserAge(age: String) = saveString(USER_AGE, age)
    suspend fun saveUserEmail(email: String) = saveString(USER_EMAIL, email)
    suspend fun saveUserNumber(number: String) = saveString(USER_NUMBER, number)
    suspend fun saveAccessToken(token: String) = saveString(SESSION_ACCEPT_TOKEN,token)
    suspend fun saveRefreshToken(token:String) = saveString(SESSION_REFRESH_TOKEN,token)

    suspend fun authenticate(value:Boolean)= saveBool(AUTHENTICATED,value)
    // --- Helper functions ---
    fun getStringFlow(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: ""
        }
    }
    suspend fun saveBool(key: Preferences.Key<Boolean>, value: Boolean){
        dataStore.edit {
            preferences ->
            preferences[key]=value
        }
    }
    fun getBoolFlow(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    suspend fun saveString(key: Preferences.Key<String>, value: String) {

            dataStore.edit { preferences ->
                preferences[key] = value
            }

    }
}