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

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnCancel: Button = findViewById(R.id.btnCancel)
        val Email: EditText = findViewById(R.id.edtFPEmail)
        val btnReset: Button = findViewById(R.id.btnResetPassword)
        val textView2: TextView = findViewById(R.id.textView2)
        val textView: TextView = findViewById(R.id.textView)

        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)

        textView2.startAnimation(ttb)
        textView.startAnimation(ttb)
        Email.startAnimation(stb)
        btnReset.startAnimation(btt)
        btnCancel.startAnimation(btt2)

        btnCancel.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnReset.setOnClickListener{
            if(Email.text.isEmpty())
            {
                Toast.makeText(this,
                    getString(R.string.please_input_valid_email), Toast.LENGTH_SHORT).show()
            }
            else
            {

            }
        }


    }
}