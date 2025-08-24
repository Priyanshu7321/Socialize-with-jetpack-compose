package com.example.socialize.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialize.repository.AuthRepository
import com.example.socialize.repository.DatastoreRepository
import com.example.socialize.entity.UserPassword
import com.example.socialize.repository.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val datastoreRepo: DatastoreRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _response = mutableStateOf<Map<String, String>>(emptyMap())
    val response: State<Map<String, String>> = _response

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isResponseSuccessfulOrNot = mutableStateOf<Boolean>(false)
    val isResponseSuccessfulOrNot: State<Boolean> = _isResponseSuccessfulOrNot

    fun authenticate(userPassword: UserPassword, checkMethod: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            _isLoading.value = true
            
            try {
                when (val result = repository.authenticate(userPassword, checkMethod)) {
                    is AuthResult.Success -> {
                        _authState.value = AuthState.Success(
                            if (checkMethod == "login") "Login successful!" else "Account created successfully!"
                        )
                        _isResponseSuccessfulOrNot.value = true
                        _response.value = result.data
                        
                        // Save user data to datastore
                        datastoreRepo.saveUserUsername(userPassword.username)
                        datastoreRepo.saveUserPassword(userPassword.password)
                        result.data["token"]?.let { datastoreRepo.saveToken(it) }
                    }
                    is AuthResult.Error -> {
                        val msg = when (val ex = result.exception) {
                            is HttpException -> "HTTP ${ex.code()}: ${ex.message()}"
                            else -> ex.message ?: "An unknown error occurred"
                        }
                        _authState.value = AuthState.Error(msg)
                        _isResponseSuccessfulOrNot.value = false
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "An unexpected error occurred")
                _isResponseSuccessfulOrNot.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }

    suspend fun saveTestCredentialsToDatastore(name: String, email: String, password: String) {
        datastoreRepo.saveUserName(name)
        datastoreRepo.saveUserEmail(email)
        datastoreRepo.saveUserPassword(password)
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            _isLoading.value = true
            
            try {
                when (val result = repository.signInWithGoogle(idToken)) {
                    is AuthResult.Success -> {
                        // Save user data to datastore
                        result.data["username"]?.let { datastoreRepo.saveUserUsername(it) }
                        result.data["token"]?.let { datastoreRepo.saveToken(it) }
                        
                        // Save additional user data if available
                        result.data["email"]?.let { datastoreRepo.saveUserEmail(it) }
                        result.data["name"]?.let { datastoreRepo.saveUserName(it) }
                        
                        // Update state after saving to datastore
                        _response.value = result.data
                        _isResponseSuccessfulOrNot.value = true
                        _authState.value = AuthState.Success("Successfully signed in with Google!")
                    }
                    is AuthResult.Error -> {
                        val ex = result.exception
                        val errorMessage = if (ex is HttpException) {
                            "HTTP ${ex.code()}: ${ex.message()}"
                        } else {
                            ex.message ?: "Google Sign-In failed"
                        }
                        Log.e("GoogleSignIn", errorMessage)
                        _authState.value = AuthState.Error(errorMessage)
                        _isResponseSuccessfulOrNot.value = false
                    }
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An unexpected error occurred during Google Sign-In"
                Log.e("GoogleSignIn", errorMessage, e)
                _authState.value = AuthState.Error(errorMessage)
                _isResponseSuccessfulOrNot.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
