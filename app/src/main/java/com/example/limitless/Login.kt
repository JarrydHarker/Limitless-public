package com.example.limitless

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
import com.example.limitless.Exercise.Log_Exercise
import com.example.limitless.data.DbAccess
import com.example.limitless.data.PasswordHasher
import com.example.limitless.data.StepCounterService
import com.example.limitless.data.User
import com.example.limitless.data.ViewModels.ActivityViewModel
import com.example.limitless.data.ViewModels.NutritionViewModel
import com.example.limitless.data.dbAccess
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


        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        //val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val textView22 = findViewById<TextView>(R.id.textView22)
        val textView21 = findViewById<TextView>(R.id.textView21)
        val txtUsername_LG = findViewById<EditText>(R.id.txtUsername_LG)
        val textView18 = findViewById<TextView>(R.id.textView18)
        val txtPassword_LG = findViewById<EditText>(R.id.txtPassword_LG)
        val btnLogin_LG = findViewById<Button>(R.id.btnLogin_LG)
        val btnSignup_LG = findViewById<Button>(R.id.btnSignup_LG)
        val Btn2ForgotPassword = findViewById<Button>(R.id.Btn2ForgotPassword)
        val left_line = findViewById<View>(R.id.left_line)
        val right_line = findViewById<View>(R.id.right_line)
        val text_or = findViewById<TextView>(R.id.text_or)
        val google_sso_button = findViewById<Button>(R.id.google_sso_button)


        textView22.startAnimation(ttb)
        textView21.startAnimation(btt)
        txtUsername_LG.startAnimation(btt)
        textView18.startAnimation(btt2)
        txtPassword_LG.startAnimation(btt2)
        btnLogin_LG.startAnimation(btt3)
        btnSignup_LG.startAnimation(btt3)
        Btn2ForgotPassword.startAnimation(btt3)
        left_line.startAnimation(btt4)
        right_line.startAnimation(btt4)
        text_or.startAnimation(btt4)
        google_sso_button.startAnimation(btt4)

        val btnForgotPassword: Button = findViewById(R.id.Btn2ForgotPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin_LG)
        val btnSignup: Button = findViewById(R.id.btnSignup_LG)

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

            LoginUser(this, username, password){user ->
                if(user != null){
                    currentUser = user

                    currentUser?.LoadUserData{
                        nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)
                        activityViewModel = ActivityViewModel(LocalDate.now())

                        nutritionViewModel.LoadUserData()
                        activityViewModel.LoadUserData()

                        dbAccess.GetDay(LocalDate.now(), currentUser?.userId!!){ day ->
                            if(day == null){
                                currentUser?.CreateDay()
                            }else{
                                currentUser!!.currentDay = day
                            }
                        }

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this, "User not found, please sign up", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnGoogle.setOnClickListener {
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

                    handleSignIn(result){ isNewUser ->
                        if(isNewUser){
                            val intent = Intent(this@Login, User_Password::class.java)
                            startActivity(intent)// Handle the sign-in result
                        }else{
                            val intent = Intent(this@Login, MainActivity::class.java)
                            startActivity(intent)// Handle the sign-in result
                        }
                    }

                } catch (e: GetCredentialException) {
                    if (e is NoCredentialException) {
                        // Retry without filtering by authorized accounts
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

                            handleSignup(resultRetry){
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                            }

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

    private fun handleSignup(result: GetCredentialResponse, onComplete: () -> Unit) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val gId = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        currentUser = User(name = gId.givenName.toString(), surname = gId.familyName.toString(), email = gId.id)
                        currentUser!!.GenerateID()
                        activityViewModel = ActivityViewModel(LocalDate.now())
                        nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)

                        dbAccess.GetDay(LocalDate.now(), userId = currentUser?.userId!!){ day ->
                            if(day == null){
                                currentUser?.CreateDay()
                            }else{
                                currentUser!!.currentDay = day
                            }
                        }

                        onComplete()

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

fun LoginUser(context: Context, username: String, password: String, onComplete: (User?) -> Unit) {
    val hasher = PasswordHasher()

    dbAccess.GetUserByEmail(username) { user ->
        val hashedPW = hasher.HashPassword(password)

        if (user != null) {
            if (user.password == hashedPW) {
                onComplete(user)
            } else {
                // Ensure the Toast runs on the main/UI thread
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            onComplete(null)
        }
    }
}


private fun handleFailure(type: String, e: GetCredentialException) {
    }

    fun GenerateNonce(length: Int = 16): String {
        val secureRandom = SecureRandom()
        val byteArray = ByteArray(length)
        secureRandom.nextBytes(byteArray)
        return Base64.getEncoder().encodeToString(byteArray)
    }

    fun handleSignIn(result: GetCredentialResponse, onComplete: (Boolean) -> Unit) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val gId = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        dbAccess.GetUserByEmail(gId.id){ user ->
                             currentUser = user
                        }

                        if(currentUser != null){
                            activityViewModel = ActivityViewModel(LocalDate.now())
                            nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)

                            dbAccess.GetDay(LocalDate.now(), userId = currentUser?.userId!!){ day ->
                                if(day == null){
                                    currentUser?.CreateDay()
                                }else{
                                    currentUser!!.currentDay = day
                                }
                            }
                            onComplete(false)
                        }else{//Sign Up from SSO
                            currentUser = User(name = gId.givenName.toString(), surname = gId.familyName.toString(), email = gId.id)
                            activityViewModel = ActivityViewModel(LocalDate.now())
                            nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)
                            currentUser?.CreateDay()

                            onComplete(true)
                        }
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