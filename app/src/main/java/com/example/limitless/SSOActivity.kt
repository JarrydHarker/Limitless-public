package com.example.limitless

import android.content.ContentValues.TAG
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.Base64


class SSOActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ssoactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnGoogle: Button = findViewById(R.id.google_sso_button)
        val activityContext = this
        // Initialize the CredentialManager
        val credentialManager = CredentialManager.create(this)

        btnGoogle.setOnClickListener {
            /*val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder("677746774102-0mfqbkl5q7k3b207q7dmutj3mv6s81rq.apps.googleusercontent.com")
                .setNonce(GenerateNonce())  // Add any nonce generation logic here
                .build()
*/
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId("677746774102-0mfqbkl5q7k3b207q7dmutj3mv6s81rq.apps.googleusercontent.com")
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)  // Use the created Google Sign-In option
                .build()

            // Using GlobalScope for background work (not lifecycle-aware)
            lifecycleScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = activityContext,  // Ensure activityContext is defined
                    )
                    handleSignIn(result)  // Handle the sign-in result
                } catch (e: GetCredentialException) {
                    if (e is NoCredentialException) {
                        // Retry without filtering by authorized accounts
                        /*val signInWithGoogleOptionRetry: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder("677746774102-0mfqbkl5q7k3b207q7dmutj3mv6s81rq.apps.googleusercontent.com")
                            .setNonce(GenerateNonce())
                            .build()*/

                        val googleIdOptionRetry: GetGoogleIdOption = GetGoogleIdOption.Builder()
                            .setFilterByAuthorizedAccounts(false)
                            .setServerClientId("677746774102-0mfqbkl5q7k3b207q7dmutj3mv6s81rq.apps.googleusercontent.com")
                            .build()

                        val requestRetry: GetCredentialRequest = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOptionRetry)
                            .build()

                        try {
                            val resultRetry = credentialManager.getCredential(
                                request = requestRetry,
                                context = activityContext,
                            )
                            handleSignIn(resultRetry)
                        } catch (retryException: GetCredentialException) {
                            handleFailure("retry",retryException)
                        }
                    } else {
                        handleFailure("OG",e)
                    }
                }
            }
        }

    }

    private fun handleFailure(type: String, e: GetCredentialException) {
        Log.e("Fuck", type + "|" + e.toString())
    }

    fun GenerateNonce(length: Int = 16): String {
        val secureRandom = SecureRandom()
        val byteArray = ByteArray(length)
        secureRandom.nextBytes(byteArray)
        return Base64.getEncoder().encodeToString(byteArray)
    }

    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }

                }else {
                    // Catch any unrecognized credential type here.
                    Log.e(TAG, "Unexpected type of credential")
                }
            }else -> {
                // Catch any unrecognized credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }
}