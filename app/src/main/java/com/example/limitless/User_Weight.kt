package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
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
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
        //val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
        //val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val textView42 = findViewById<TextView>(R.id.textView60)
        val UW_Linear = findViewById<LinearLayout>(R.id.UW_Linear)


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

        textView42.startAnimation(ttb)
        UW_Linear.startAnimation(stb)
        next.startAnimation(btt)
        skip.startAnimation(btt2)

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