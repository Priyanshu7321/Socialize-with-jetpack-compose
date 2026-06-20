package com.example.socialize.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.socialize.service.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.socialize.BuildConfig
import com.example.socialize.service.HostSelectionInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHostInterceptor() = HostSelectionInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(hostInterceptor: HostSelectionInterceptor, @ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(hostInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                val startTime = System.currentTimeMillis()
                
                android.util.Log.d("HTTP_REQUEST", "→ ${request.method} ${request.url}")
                android.util.Log.d("HTTP_REQUEST", "Headers: ${request.headers}")
                
                val response = chain.proceed(request)
                val endTime = System.currentTimeMillis()
                
                android.util.Log.d("HTTP_RESPONSE", "← ${response.code} ${request.url} (${endTime - startTime}ms)")
                android.util.Log.d("HTTP_RESPONSE", "Response Headers: ${response.headers}")
                
                response
            }
            .addInterceptor(ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .alwaysReadResponseBody(true)
                .build())

            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://default.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
