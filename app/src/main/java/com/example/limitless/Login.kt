package com.example.limitless

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.data.DbAccess
import com.example.limitless.data.PasswordHasher
import com.example.limitless.data.User

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

        val btnLogin: Button = findViewById(R.id.btnLogin_LG)
        val btnSignup: Button = findViewById(R.id.btnSignup_LG)
        val btnSkip: Button = findViewById(R.id.btnSkip)
        val txtUsername: EditText = findViewById(R.id.txtUsername_LG)
        val txtPassword: EditText = findViewById(R.id.txtPassword_LG)

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

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "User not found, please sign up", Toast.LENGTH_LONG).show()
            }
        }

        btnSkip.setOnClickListener{
            currentUser = User()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
}