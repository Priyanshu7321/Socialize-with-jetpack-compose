package com.example.socialize.service

import com.example.socialize.entity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body user: User): Response<Map<String, String>>

    @POST("signup")
    suspend fun signup(@Body user: User): Response<Map<String, String>>
}
