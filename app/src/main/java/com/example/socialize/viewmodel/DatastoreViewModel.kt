package com.example.socialize.viewmodel

import android.R
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialize.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthLoginState {
    object Loading : AuthLoginState()
    object Authenticated : AuthLoginState()
    object Unauthenticated : AuthLoginState()
}
@HiltViewModel
class DatastoreViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) : ViewModel() {

    // Expose datastore flows directly for better performance
    val userNameFlow = datastoreRepository.userNameFlow
    val userEmailFlow = datastoreRepository.userEmailFlow
    val baseUrlFlow = datastoreRepository.baseUrl
    val authenticatedFlow: Flow<Boolean> =
        datastoreRepository.authenticated

    // ✅ ADD THIS HERE
    val authStateFlow: Flow<AuthLoginState> =
        authenticatedFlow.map { isLoggedIn ->
            if (isLoggedIn) AuthLoginState.Authenticated
            else AuthLoginState.Unauthenticated
        }
    fun <T> getFlow(key: Preferences.Key<T>): Flow<T?> {
        return datastoreRepository.getFlow(key)
    }


    public fun <T> saveData(key: Preferences.Key<T>, value: Any) {

        viewModelScope.launch {
            when (value) {
                is String -> datastoreRepository.saveString(
                    key as Preferences.Key<String>, value as String
                )


                is Boolean -> datastoreRepository.saveBool(
                    key as Preferences.Key<Boolean>, value as Boolean
                )

            }
        }
    }
}
