package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        btnCancel.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnReset.setOnClickListener{
            if(Email.text.isEmpty())
            {
                Toast.makeText(this, "Please Input Valid Email", Toast.LENGTH_SHORT).show()
            }
            else
            {

            }
        }


    }
}