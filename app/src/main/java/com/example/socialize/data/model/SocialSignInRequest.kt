package com.example.socialize.data.model

data class SocialSignInRequest(
    val provider: String = "google",
    val idToken: String,
    val accessToken: String = "" // Not used for Google, but kept for other providers
)

data class SocialSignInResponse(
    val token: String,
    val user: User
)

data class User(
    val id: String,
    val email: String,
    val name: String
)
