package com.main.limitless

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.main.limitless.data.User

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
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
        //val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val btnSignUp: Button = findViewById(R.id.SU_btnSignIn)
        val SU_lblEmail = findViewById<TextView>(R.id.SU_lblEmail)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val SU_lblPassword = findViewById<TextView>(R.id.SU_lblPassword)
        val txtEmail: EditText = findViewById(R.id.SU_txtUsername)
        val txtPassword: EditText = findViewById(R.id.SU_txtPassword)
        val btnBack: Button = findViewById(R.id.SU_btnBack)

        textView3.startAnimation(ttb)
        SU_lblEmail.startAnimation(btt)
        txtEmail.startAnimation(btt)
        SU_lblPassword.startAnimation(btt2)
        txtPassword.startAnimation(btt2)
        btnSignUp.startAnimation(btt3)
        btnBack.startAnimation(btt3)

        btnBack.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this, getString(R.string.please_enter_email), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                Toast.makeText(this, getString(R.string.please_enter_password1), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            currentUser = User()

            currentUser?.email = email
            currentUser?.password = password

            saveSignUp(email, password)

            val intent = Intent(this, User_Password::class.java)
            startActivity(intent)
        }
    }

    fun saveSignUp(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }
}