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
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val repository: AuthRepository,private val datastoreRepo : DatastoreRepository
) : ViewModel() {

    private val _response = mutableStateOf<Map<String, String>>(emptyMap())
    val response: State<Map<String, String>> = _response

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isresponseSuccessfulorNot = mutableStateOf<Boolean>(false);
    var isresponseSuccessfulorNot:State<Boolean> = _isresponseSuccessfulorNot

    fun authenticate(userPassword: UserPassword,checkMethod:String) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.authenticate(userPassword, checkMethod)) {
                is AuthResult.Success -> {
                    _response.value = result.data
                    datastoreRepo.saveUserUsername(userPassword.username)
                    datastoreRepo.saveUserPassword(userPassword.password)
                    result.data["token"]?.let { datastoreRepo.saveToken(it) }
                    _isresponseSuccessfulorNot.value = true
                }
                is AuthResult.Error -> {
                    _isresponseSuccessfulorNot.value = false
                }
            }
            _isLoading.value = false
        }
    }

    suspend fun saveTestCredentialsToDatastore(name: String, email: String, password: String) {
        datastoreRepo.saveUserName(name)
        datastoreRepo.saveUserEmail(email)
        datastoreRepo.saveUserPassword(password)
    }
}
