package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class User_Weight : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_weight)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val next: Button = findViewById(R.id.UW_btnNext)
        val skip: Button = findViewById(R.id.UW_btnSkip)

        val npMain: NumberPicker = findViewById(R.id.UW_Main)
        npMain.minValue = 0
        npMain.maxValue = 500

        val npSecondary: NumberPicker = findViewById(R.id.UW_Secondary)
        npSecondary.minValue = 0
        npSecondary.maxValue = 9

        val npText: TextView = findViewById(R.id.UW_txtUnit)
        npText.text = "kg"

        next.setOnClickListener{
            if(npMain.value != 0)
            {
                val weight: Double = (npMain.value + (npSecondary.value/10)).toDouble()
                currentUser?.SetWeight(weight)

                val intent = Intent(this, User_WeightGoal::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please fill in field or press Skip to continue", Toast.LENGTH_SHORT).show()
            }
        }
        skip.setOnClickListener{
            val intent = Intent(this, User_WeightGoal::class.java)
            startActivity(intent)
        }

    }
}