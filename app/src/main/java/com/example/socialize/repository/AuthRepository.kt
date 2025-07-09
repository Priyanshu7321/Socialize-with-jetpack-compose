package com.example.socialize.repository

import com.example.socialize.service.ApiService
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import com.example.socialize.entity.UserPassword

sealed class AuthResult {
    data class Success(val data: Map<String, String>) : AuthResult()
    data class Error(val exception: Throwable) : AuthResult()
}

@Singleton
class AuthRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun authenticate(user: UserPassword, checkMethod: String): AuthResult {
        return try {
            val response = if (checkMethod == "signup") {
                apiService.signup(user)
            } else {
                apiService.login(user)
            }
            if (response.isSuccessful) {
                AuthResult.Success(response.body() ?: emptyMap())
            } else {
                AuthResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }
}