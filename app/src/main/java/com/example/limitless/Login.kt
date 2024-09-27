package com.example.limitless

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.limitless.data.DbAccess
import com.example.limitless.data.PasswordHasher
import com.example.limitless.data.User
import com.example.limitless.data.ViewModels.ActivityViewModel
import com.example.limitless.data.ViewModels.NutritionViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.time.LocalDate
import java.util.Base64

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnMap = findViewById<Button>(R.id.btnMap)

        btnMap.setOnClickListener {
            val intent = Intent(this, Map_Activity::class.java)
            startActivity(intent)
        }

        val btnForgotPassword: Button = findViewById(R.id.Btn2ForgotPassword)

        val btnLogin: Button = findViewById(R.id.btnLogin_LG)
        val btnSignup: Button = findViewById(R.id.btnSignup_LG)
        val btnSkip: Button = findViewById(R.id.btnSkip)
        val txtUsername: EditText = findViewById(R.id.txtUsername_LG)
        val txtPassword: EditText = findViewById(R.id.txtPassword_LG)
        val btnGoogle: Button = findViewById(R.id.google_sso_button)
        val activityContext = this
        // Initialize the CredentialManager
        val credentialManager = CredentialManager.create(this)

        btnForgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        btnSignup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()

            if(username.isEmpty()){
                Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val user = LoginUser(this, username, password)

            if(user != null){
                currentUser = user

                nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)
                activityViewModel = ActivityViewModel(LocalDate.now())

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "User not found, please sign up", Toast.LENGTH_LONG).show()
            }
        }

        btnSkip.setOnClickListener{
            currentUser = User()

            // Initialize ViewModel with calorieWallet from currentUser
            nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)
            activityViewModel = ActivityViewModel(LocalDate.now())

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnGoogle.setOnClickListener {
            /*val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder("677746774102-0mfqbkl5q7k3b207q7dmutj3mv6s81rq.apps.googleusercontent.com")
                .setNonce(GenerateNonce())  // Add any nonce generation logic here
                .build()
*/
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId("651564228412-8jnt2ktgb86rcdsh1kbh7loak5244hdm.apps.googleusercontent.com")
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
                            .setServerClientId("651564228412-8jnt2ktgb86rcdsh1kbh7loak5244hdm.apps.googleusercontent.com")
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

    fun LoginUser(context: Context, username: String, password: String): User?{
        val dbAccess = DbAccess.GetInstance()
        val hasher = PasswordHasher()

        val user = dbAccess.GetUser(username)
        val hashedPW = hasher.HashPassword(password)

        if(user != null){
            if(user.password == hashedPW){
                return user
            }else Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_LONG).show()
        }

        return null
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