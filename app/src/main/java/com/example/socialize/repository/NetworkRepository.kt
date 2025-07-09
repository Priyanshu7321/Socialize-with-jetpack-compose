package com.example.socialize.repository

import com.example.socialize.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val apiService: ApiService){

}