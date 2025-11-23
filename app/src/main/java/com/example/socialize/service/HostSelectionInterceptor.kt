package com.example.socialize.service;

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
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newHost = host
        if (newHost != null) {
            val newUrl = request.url.newBuilder()
                .scheme("http")
                .host(newHost)
                .build()

            val newRequest = request.newBuilder()
                .url(newUrl)
                .build()

            return chain.proceed(newRequest)
        }

        return chain.proceed(request)
    }
}
