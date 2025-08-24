package com.example.socialize.data.repository

import com.example.socialize.data.model.SocialSignInRequest
import com.example.socialize.data.model.SocialSignInResponse
import com.example.socialize.service.ApiService
import com.example.socialize.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun socialSignIn(idToken: String): Result<Response<Map<String, String>>> {
        return try {
            val response = apiService.socialSignIn(
                SocialSignInRequest(idToken = idToken)
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
