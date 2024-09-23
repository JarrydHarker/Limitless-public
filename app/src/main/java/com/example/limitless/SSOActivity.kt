package com.example.limitless

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
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

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId("WEB_CLIENT_ID")
            .setAutoSelectEnabled(true)
            .setNonce(GenerateNonce())
        .build()
    }

    fun GenerateNonce(length: Int = 16): String {
        val secureRandom = SecureRandom()
        val byteArray = ByteArray(length)
        secureRandom.nextBytes(byteArray)
        return Base64.getEncoder().encodeToString(byteArray)
    }
}