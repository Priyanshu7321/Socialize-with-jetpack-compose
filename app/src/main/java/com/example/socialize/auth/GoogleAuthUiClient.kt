package com.example.socialize.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.example.socialize.R

class GoogleAuthUiClient(
    private val context: Context
) {
    private val auth = Firebase.auth
    private val oneTapClient: SignInClient = Identity.getSignInClient(context)

    suspend fun beginSignIn(): IntentSender? {
        return try {
            val result = oneTapClient.beginSignIn(
                BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            .setServerClientId(context.getString(R.string.default_web_client_id))
                            .setFilterByAuthorizedAccounts(false)
                            .build()
                    )
                    .build()
            ).await()
            result.pendingIntent.intentSender
        } catch (e: Exception) {
            null
        }
    }

    suspend fun signInWithIntent(data: Intent): Result<SignInWithGoogle> {
        return try {
            val credential = oneTapClient.getSignInCredentialFromIntent(data)
            val googleIdToken = credential.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = auth.signInWithCredential(googleCredentials).await()

            Result.success(
                SignInWithGoogle(
                    userId = authResult.user?.uid ?: "",
                    idToken = googleIdToken ?: "",
                    email = authResult.user?.email ?: "",
                    name = authResult.user?.displayName ?: ""
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        const val REQUEST_CODE_ONE_TAP = 1001
    }
}

data class SignInWithGoogle(
    val userId: String,
    val idToken: String,
    val email: String,
    val name: String
)
