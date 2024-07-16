@file:Suppress("DEPRECATION")

package com.martinszuc.clientsapp.ui.component.login

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.martinszuc.clientsapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val oneTapClient: SignInClient
) : ViewModel() {

    private val logTAG = "LoginViewModel"

    fun startSignIn(activity: Activity, launcher: ActivityResultLauncher<IntentSenderRequest>) {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(activity.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    launcher.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(logTAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(activity) { e ->
                Log.e(logTAG, "One Tap sign-in failed: ${e.localizedMessage}")
            }
    }

    fun handleSignInResult(data: Intent?, onLoginSuccess: () -> Unit, onLoginFailed: (Exception) -> Unit) {
        try {
            val credential: SignInCredential = oneTapClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            when {
                idToken != null -> {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    firebaseAuth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(logTAG, "signInWithCredential:success")
                                onLoginSuccess()
                            } else {
                                Log.w(logTAG, "signInWithCredential:failure", task.exception)
                                onLoginFailed(task.exception ?: Exception("Sign-in failed"))
                            }
                        }
                }
                else -> {
                    Log.d(logTAG, "No ID token!")
                    onLoginFailed(Exception("No ID token"))
                }
            }
        } catch (e: Exception) {
            Log.e(logTAG, "Sign-in failed", e)
            onLoginFailed(e)
        }
    }
}