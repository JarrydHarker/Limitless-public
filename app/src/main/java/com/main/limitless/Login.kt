package com.main.limitless

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.lifecycleScope
import com.main.limitless.data.PasswordHasher
import com.main.limitless.data.User
import com.main.limitless.data.ViewModels.ActivityViewModel
import com.main.limitless.data.ViewModels.NutritionViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.main.limitless.data.NetworkMonitor
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.time.LocalDate
import java.util.Base64
import java.util.concurrent.Executor

class Login : AppCompatActivity() {
    lateinit var info: String
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private  lateinit var btn: Button
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOnline = true
            }

            override fun onLost(network: Network) {
                isOnline = false
            }
        }

        networkMonitor = NetworkMonitor(this)
        networkMonitor.registerNetworkCallback(networkCallback)

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

        if(currentUser == null){
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val username = sharedPreferences.getString("username", null)
            val password = sharedPreferences.getString("password", null)

            if (username != null && password != null) {
                setupBiometricAuthentication(this) { isSupported ->
                    if (isSupported) {
                        biometricPrompt.authenticate(promptInfo)
                    }
                }
            }
        }

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
                Toast.makeText(this, getString(R.string.please_enter_username), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                Toast.makeText(this, getString(R.string.please_enter_password), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(isOnline){
                LoginUser(this, username, password){user ->
                    if(user != null){
                        saveLogin(username, password)
                        currentUser = user

                        currentUser?.LoadUserData{
                            nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)
                            activityViewModel = ActivityViewModel(LocalDate.now())

                            nutritionViewModel.LoadUserData()
                            activityViewModel.LoadUserData(this)

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
                        runOnUiThread {
                            Toast.makeText(this, getString(R.string.user_not_found_please_sign_up), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }else{
                val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                val localUsername = sharedPreferences.getString("username", null)
                val localPassword = sharedPreferences.getString("password", null)
                val hasher = PasswordHasher()

                if(localUsername != null && localPassword != null){
                    if(localUsername == username && localPassword == hasher.HashPassword(password)){
                        currentUser = User("Temp", "Firstname", "Surname", username, password)
                        activityViewModel.LoadUserData(this)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, "You have to login at least once with an internet connection", Toast.LENGTH_LONG).show()
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

    override fun onDestroy() {
        super.onDestroy()

        networkMonitor.unregisterNetworkCallback(networkCallback)
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

    private fun setupBiometricAuthentication(context: Context, onComplete: (Boolean) -> Unit) {
        // Initialize biometricPrompt here, so it’s ready regardless of the check
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    val sharedPreferences = getSharedPreferences("user_prefs_online", MODE_PRIVATE)
                    val username = sharedPreferences.getString("username", null)
                    val password = sharedPreferences.getString("password", null)

                    if(isOnline){
                        if (username != null && password != null) {
                            dbAccess.GetUserByEmail(username) { user ->
                                if (user != null) {
                                    if (user.password == password) {
                                        Log.d("ToHashOrNotToHash", "Username: $username\nPassword: $password")
                                        saveLoginOffline(username, password)
                                        currentUser = user
                                        currentUser?.LoadUserData {
                                            nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)
                                            activityViewModel = ActivityViewModel(LocalDate.now())

                                            nutritionViewModel.LoadUserData()
                                            activityViewModel.LoadUserData(context)

                                            dbAccess.GetDay(LocalDate.now(), currentUser?.userId!!) { day ->
                                                if (day == null) {
                                                    currentUser?.CreateDay()
                                                } else {
                                                    currentUser!!.currentDay = day
                                                }
                                            }
                                        }
                                        Log.d("ToHashOrNotToHash", "Should Intent")
                                        val intent = Intent(this@Login, MainActivity::class.java)
                                        startActivity(intent)
                                    }else{
                                        // Ensure the Toast runs on the main/UI thread
                                        Handler(Looper.getMainLooper()).post {
                                            Toast.makeText(context,
                                                context.getString(R.string.incorrect_username_or_password), Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } else {
                                    // Ensure the Toast runs on the main/UI thread
                                    Handler(Looper.getMainLooper()).post {
                                        Toast.makeText(context,
                                            context.getString(R.string.incorrect_username_or_password), Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }else{
                        currentUser = User("Temp", "Firstname", "Surname", username!!, password!!)
                        activityViewModel.LoadUserData(this@Login)

                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                /*override fun onAuthenticationFailed() {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }*/
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()

        // Check biometric support and then complete
        val biometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
            onComplete(true)
        } else {
            onComplete(false)
        }
    }

    fun saveLoginOffline(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val hasher = PasswordHasher()

        editor.putString("username", username)
        editor.putString("password", hasher.HashPassword(password))
        editor.apply()
    }

    fun saveLogin(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("user_prefs_online", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }


    fun checkDeviceHasBiometric() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                info = "App can authenticate using biometrics."
                btn.isEnabled = true

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
                info = "No biometric features available on this device."
                btn.isEnabled = false

            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                info = "Biometric features are currently unavailable."
                btn.isEnabled = false

            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(android.provider.Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(android.provider.Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }
                btn.isEnabled = false

                startActivityForResult(enrollIntent, 100)
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
                    Toast.makeText(context,
                        context.getString(R.string.incorrect_username_or_password), Toast.LENGTH_LONG).show()
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