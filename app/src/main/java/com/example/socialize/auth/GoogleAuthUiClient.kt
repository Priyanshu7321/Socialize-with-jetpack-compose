package com.example.socialize.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.example.socialize.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class GoogleAuthUiClient(
    private val context: Context
) {

    private val auth = Firebase.auth

    private val credentialManager =
        CredentialManager.create(context)

    suspend fun signIn(): Result<SignInWithGoogle> {
        return try {

            // 1. Configure Google Sign-In
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(
                    context.getString(R.string.default_web_client_id)
                )
                .setFilterByAuthorizedAccounts(true)
                .build()

            // 2. Create Credential Manager request
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            // 3. Ask Credential Manager for credential
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            // 4. Get returned credential
            val credential = result.credential

            if (
                credential is CustomCredential &&
                credential.type ==
                GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {

                // 5. Convert returned data into Google ID token credential
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(
                        credential.data
                    )

                val googleIdToken =
                    googleIdTokenCredential.idToken

                // 6. Convert Google token to Firebase credential
                val firebaseCredential =
                    GoogleAuthProvider.getCredential(
                        googleIdToken,
                        null
                    )
                // 7. Authenticate with Firebase
                val authResult =
                    auth.signInWithCredential(
                        firebaseCredential
                    ).await()

                // 8. Return application user information
                Result.success(
                    SignInWithGoogle(
                        userId = authResult.user?.uid ?: "",
                        idToken = googleIdToken,
                        email = authResult.user?.email ?: "",
                        name = authResult.user?.displayName ?: ""
                    )
                )

            } else {
                Result.failure(
                    Exception("Unexpected credential type")
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

data class SignInWithGoogle(
    val userId: String,
    val idToken: String,
    val email: String,
    val name: String
)
