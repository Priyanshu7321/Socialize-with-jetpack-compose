package com.example.socialize.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialize.service.HostSelectionInterceptor
import com.example.socialize.repository.AuthRepository
import com.example.socialize.repository.DatastoreRepository
import com.example.socialize.entity.UserPassword
import com.example.socialize.repository.ApiResponseListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
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
    private val datastoreRepo: DatastoreRepository,
    private val interceptor: HostSelectionInterceptor
) : ViewModel() {

    private val _authState = MutableStateFlow<String>("")
    val authState: StateFlow<String> = _authState

    private val _response = mutableStateOf<Map<String, String>>(emptyMap())
    val response: State<Map<String, String>> = _response

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isResponseSuccessfulOrNot = mutableStateOf<Boolean>(false)
    val isResponseSuccessfulOrNot: State<Boolean> = _isResponseSuccessfulOrNot

     fun authenticate(userPassword: UserPassword, checkMethod: String) {
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                repository.authenticate(userPassword, checkMethod, object: ApiResponseListener{
                    override fun Success(data: Map<String, String>) {
                        _isLoading.value = false
                        _authState.value = if (checkMethod == "login"){ "Login successful!" }else{ "Account created successfully!"}

                        _isResponseSuccessfulOrNot.value = true
                        _response.value = data

                        // Save user data to datastore
                        viewModelScope.launch(Dispatchers.IO) {
                            Log.d("tokens",_response.value.toString());
                            if (userPassword.email!="") {
                                datastoreRepo.saveUserUsername(userPassword.email)
                                datastoreRepo.saveAccessToken(response.value["access_token"] ?: "")
                                datastoreRepo.saveRefreshToken(response.value["refresh_token"] ?: "")

                            }
                        }
                    }

                    override fun Error(errorMsg: String) {
                        _isLoading.value = false
                        _authState.value = errorMsg

                    }
                })
            }  catch (e: Exception) {
                _authState.value =  "An unexpected error occurred"
                _isResponseSuccessfulOrNot.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updateBaseUrl(url: String) {
        interceptor.host = url.toHttpUrlOrNull() as String?
    }

//    fun resetAuthState() {
//        _authState.value = AuthState.Idle
//    }

    suspend fun saveTestCredentialsToDatastore(name: String, email: String, password: String) {
        datastoreRepo.saveUserName(name)
        datastoreRepo.saveUserEmail(email)
        datastoreRepo.saveUserPassword(password)
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
//            _authState.value = AuthState.Loading
            _isLoading.value = true
            
            try {
                repository.signInWithGoogle(idToken,object : ApiResponseListener{
                    override fun Success(data: Map<String, String>) {
                        _isLoading.value = false
                        _authState.value = "successfully signed up"
                        if(data!=null){
                            _response.value = data
                        }
                    }

                    override fun Error(errorMsg: String) {
                        _isLoading.value = false
                        _authState.value = errorMsg
                    }

                })
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An unexpected error occurred during Google Sign-In"
                Log.e("GoogleSignIn", errorMessage, e)
                _authState.value =  "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
