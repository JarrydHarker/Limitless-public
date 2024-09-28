package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class User_CalorieWallet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_calorie_wallet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val next: Button = findViewById(R.id.UCW_btnNext)
        val skip: Button = findViewById(R.id.UCW_btnSkip)

        val userCalories: EditText = findViewById(R.id.UCW_txtCalories)

        val npText: TextView = findViewById(R.id.UCW_txtUnit)
        npText.text = "calories"

        next.setOnClickListener{
            if(userCalories.text.isNotEmpty())
            {
                Toast.makeText(this, "Calorie Wallet Captured: ${userCalories.text} ${npText.text}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, User_DailySteps::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please fill in field or press Skip to continue", Toast.LENGTH_SHORT).show()

            }
        }
        skip.setOnClickListener{
            val intent = Intent(this, User_DailySteps::class.java)
            startActivity(intent)
        }
    }
}