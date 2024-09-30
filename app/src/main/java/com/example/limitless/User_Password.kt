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

class User_Password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val Next: Button = findViewById(R.id.UP_btnNext)
        val Password: EditText = findViewById(R.id.UP_txtPassword)

        Next.setOnClickListener{
            val pw = Password.text.toString()
            if(pw.isNotEmpty()){
                currentUser?.password = pw

                val intent = Intent(this, User_Details::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}