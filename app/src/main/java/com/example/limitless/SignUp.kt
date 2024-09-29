package com.example.limitless

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
import com.example.limitless.data.User
import com.example.limitless.data.ViewModels.ActivityViewModel
import com.example.limitless.data.ViewModels.NutritionViewModel
import java.time.LocalDate

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
        val test: Button = findViewById(R.id.btnTest)

        textView3.startAnimation(ttb)
        SU_lblEmail.startAnimation(btt)
        txtEmail.startAnimation(btt)
        SU_lblPassword.startAnimation(btt2)
        txtPassword.startAnimation(btt2)
        btnSignUp.startAnimation(btt3)


        test.setOnClickListener{
            val intent = Intent(this, User_Details::class.java)
            startActivity(intent)
        }


        btnSignUp.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            currentUser = User()

            currentUser?.email = email
            currentUser?.password = password

            val intent = Intent(this, User_Details::class.java)
            startActivity(intent)
        }
    }


}