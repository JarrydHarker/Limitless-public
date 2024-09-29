package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.data.User

class User_Height : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_height)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val next: Button = findViewById(R.id.UH_btnNext)
        val skip:Button = findViewById(R.id.UH_btnSkip)

        val npMain: NumberPicker = findViewById(R.id.UH_Main)
        npMain.minValue = 0
        npMain.maxValue = 300

        val npSecondary: NumberPicker = findViewById(R.id.UH_Secondary)
        npSecondary.minValue = 0
        npSecondary.maxValue = 9

        val npText: TextView = findViewById(R.id.UH_txtUnit)
        npText.text = "cm"

        next.setOnClickListener{
            if(npMain.value != 0)
            {
                val height: Double = (npMain.value + (npSecondary.value/10)).toDouble()
                currentUser?.SetHeight(height)

                val intent = Intent(this, User_Weight::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please fill in field or press Skip to continue", Toast.LENGTH_SHORT).show()
            }
        }
        skip.setOnClickListener{
            val intent = Intent(this, User_Weight::class.java)
            startActivity(intent)
        }
    }
}