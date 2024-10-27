package com.example.limitless

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SSO_Password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sso_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtPassword = findViewById<EditText>(R.id.txtPassword_SSO)
        val txtConfirmPass = findViewById<EditText>(R.id.txtConfirm_SSO)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm_SSO)
        val btnBack = findViewById<Button>(R.id.btnBack_SSO)

        btnConfirm.setOnClickListener {
            if(txtPassword.text.length >= 8 && txtPassword.text.equals(txtConfirmPass.text.toString())){

            }else{
                Toast.makeText(this,
                    getString(R.string.make_sure_password_is_correct_and_more_than_8_letters), Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {

        }

    }
}