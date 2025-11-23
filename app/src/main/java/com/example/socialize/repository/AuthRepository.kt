package com.example.socialize.repository

import com.example.socialize.service.ApiService
import com.example.socialize.data.model.SocialSignInRequest
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import com.example.socialize.entity.UserPassword

interface ApiResponseListener {
    fun Success( data: Map<String, String>)
    fun Error(errorMsg: String)
}

@Singleton
class AuthRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun authenticate(user: UserPassword, checkMethod: String,listener: ApiResponseListener) {
        return try {
            val response = if (checkMethod == "signup") {
                apiService.signup(user)
            } else {
                apiService.login(user)
            }
            if (response.isSuccessful) {
                listener.Success(response.body() ?: emptyMap())
            } else {
                listener.Error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            listener.Error("runtimeIssue");
        }
    }

    suspend fun signInWithGoogle(idToken: String,listener: ApiResponseListener) {
        return try {
            val request = SocialSignInRequest(
                provider = "google",
                idToken = idToken
            )
            val response = apiService.socialSignIn(request)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody["token"] != null) {
                    listener.Success(responseBody)
                } else {
                    listener.Error("Invalid response from server")
                }
            } else {
                listener.Error("invalid response")
            }
        } catch (e: Exception) {
            listener.Error("invalid");
        }
    }
}