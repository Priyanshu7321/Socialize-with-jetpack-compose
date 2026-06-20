package com.example.socialize.service;

import android.util.Log
import kotlin.jvm.Volatile;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HostSelectionInterceptor @Inject constructor() : Interceptor {

    @Volatile
    var host: String? = null

    fun setBaseUrl(newUrl: String) {
        host = newUrl
        Log.d("HostSelectionInterceptor", "Base URL updated to: $newUrl")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url.toString()
        
        Log.d("HostSelectionInterceptor", "Original request URL: $originalUrl")

        val newHost = host
        if (newHost != null) {
            Log.d("HostSelectionInterceptor", "Custom host configured: $newHost")
            
            // Parse the host and port from the URL
            val hostAndPort = newHost.split(":")
            val hostname = hostAndPort[0]
            val port = if (hostAndPort.size > 1) hostAndPort[1].toIntOrNull() ?: 80 else 80
            
            Log.d("HostSelectionInterceptor", "Parsed hostname: $hostname, port: $port")
            
            val newUrl = request.url.newBuilder()
                .scheme("http")
                .host(hostname)
                .port(port)
                .build()

            val newRequest = request.newBuilder()
                .url(newUrl)
                .build()

            val finalUrl = newRequest.url.toString()
            Log.d("HostSelectionInterceptor", "Final request URL: $finalUrl")
            Log.d("HostSelectionInterceptor", "Making request to: ${newRequest.method} $finalUrl")

            return chain.proceed(newRequest)
        } else {
            Log.d("HostSelectionInterceptor", "No custom host configured, using original URL: $originalUrl")
        }

        return chain.proceed(request)
    }
}
